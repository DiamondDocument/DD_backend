package diamondpick.dd_backend.zzy.ServiceImp;

import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.FileService;
import diamondpick.dd_backend.Service.UserService;
import diamondpick.dd_backend.Service.yyh.UserServiceImp;
import diamondpick.dd_backend.zzy.Exception.DocNotExist;
import diamondpick.dd_backend.zzy.Exception.ImageNotExist;
import diamondpick.dd_backend.zzy.Exception.UserNotExist;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.URLConnection;
import java.security.MessageDigest;

@Service
public class FileServiceImp implements FileService {
    private DocumentService documentService = new DocumentImp();
    private UserService userService = new UserServiceImp();

    private String baseLocation = "C:/Users/18389/Desktop/DD_file/";
//    private String baseLocation = "/home/diamond/DD_file/";
    private String avatarLocation = baseLocation + "avatar/";
    private String documentLocation = baseLocation + "document/";
    private String imageLocation = baseLocation + "image/";

    private String baseUrl = "http://localhost/api/document/img/";

    @Override
    public void saveAvatar(String userId, MultipartFile file) throws UserNotExist, IOException {
        if (userService.selectUserByUserId(userId) == null) throw new UserNotExist();
        //先删除后保存
        File dir = new File(avatarLocation);
        String[] fileNames = dir.list();
        for(String name : fileNames){
            if(name.matches(userId + ".*")){
                File avatar = new File(avatarLocation + name);
                avatar.delete();
            }
        }
        //后缀不变，前缀改成用户名
        String[] nameSplit = file.getOriginalFilename().split(".");
        File newAvatar = new File(avatarLocation + userId + "." + nameSplit[nameSplit.length - 1]);
        newAvatar.createNewFile();
        FileOutputStream out = new FileOutputStream(newAvatar);
        out.write(file.getBytes());
    }

    @Override
    public void saveDocument(String docId, String content) throws DocNotExist, IOException {
        documentService.selectDocByDocId(docId);
        //后缀不变，前缀改成用户名
        File doc = new File(documentLocation + docId + "." + "html");
        doc.delete();
        doc.createNewFile();
        FileOutputStream out = new FileOutputStream(doc);
        out.write(content.getBytes());
    }

    @Override
    public String saveDocumentImg(MultipartFile file) throws IOException{
        //保存为md5值
        String[] nameSplit = file.getOriginalFilename().split(".");
        String name = md5HSashCode(file.getInputStream()) + "." + nameSplit[nameSplit.length - 1];
        File newImage = new File(imageLocation + name);
        newImage.delete();
        newImage.createNewFile();
        FileOutputStream out = new FileOutputStream(newImage);
        out.write(file.getBytes());
        return baseUrl + name;
    }

    @Override
    public byte[] getAvatar(String userId) throws UserNotExist, IOException{
        if (userService.selectUserByUserId(userId) == null) throw new UserNotExist();
        //先删除后保存
        File dir = new File(avatarLocation);
        String[] fileNames = dir.list();
        for(String name : fileNames){
            if(name.matches(userId + ".*")){
                File avatar = new File(avatarLocation + name);
                FileInputStream out = new FileInputStream(avatar);
                byte[] ret = null;
                out.read(ret);
                return ret;
            }
        }
        throw new UserNotExist();
    }

    @Override
    public byte[] getDocument(String docId)throws DocNotExist, IOException{
        documentService.selectDocByDocId(docId);
        //先删除后保存
        File doc = new File(documentLocation + docId + ".html");
        try{
            FileInputStream out = new FileInputStream(doc);
            byte[] ret = null;
            out.read(ret);
            return ret;
        }catch (FileNotFoundException e){
            throw new DocNotExist();
        }
    }

    @Override
    public byte[] getImage(String fileName)throws ImageNotExist, IOException {
        //先删除后保存
        File image = new File(imageLocation + fileName);
        try{
            FileInputStream out = new FileInputStream(image);
            byte[] ret = null;
            out.read(ret);
            return ret;
        }catch (FileNotFoundException e){
            throw new ImageNotExist();
        }
    }

    @Override
    public String getAvatarContentType(String userId)throws UserNotExist {
        if (userService.selectUserByUserId(userId) == null) throw new UserNotExist();
        //先删除后保存
        File dir = new File(avatarLocation);
        String[] fileNames = dir.list();
        for(String name : fileNames){
            if(name.matches(userId + ".*")){
                File avatar = new File(avatarLocation + name);
                return URLConnection.guessContentTypeFromName(name);
            }
        }
        throw new UserNotExist();
    }

    @Override
    public String getImageContentType(String fileName){
        return URLConnection.guessContentTypeFromName(fileName);
    }


    public String md5HSashCode(InputStream in) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = in.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            in.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
