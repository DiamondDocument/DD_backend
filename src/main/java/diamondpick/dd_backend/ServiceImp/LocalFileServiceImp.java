package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Service.LocalFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


/**存储的所有文件都存放在了程序运行时所在目录的/DD_file文件夹下*/
@Service
public class LocalFileServiceImp implements LocalFileService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DocumentDao documentDao;

    private String baseLocation = "C:/Users/18389/Desktop/DD_file/";
    private String userAvatarLocation = baseLocation + "user/avatar/";
    private String documentLocation = baseLocation + "document/";
    private String imageLocation = baseLocation + "image/";
    private String templateLocation = baseLocation + "template/";
    private String teamAvatarLocation = baseLocation + "team/avatar/";

    private String imgBaseUrl = "http://43.138.71.108/api/document/img/";

    public LocalFileServiceImp() {
        File workDir = new File("");
        baseLocation = workDir.getAbsolutePath() + "/DD_file";
        if(System.getenv().get("IS_SERVICE") == null){
            imgBaseUrl = "http://localhost/api/document/img/";
        }
        File f = new File(baseLocation);
        f.mkdir();
        f = new File(userAvatarLocation);
        f.mkdir();
        f = new File(documentLocation);
        f.mkdir();
        f = new File(imageLocation);
        f.mkdir();
        f = new File(teamAvatarLocation);
        f.mkdir();
        f = new File(templateLocation);
        f.mkdir();
    }

    public void saveAvatar(String Id, MultipartFile file, String avatarLocation) throws NotExist, OtherFail {
        if(userDao.selectUser(Id) == null) throw new UserNotExist();
        //先删除后保存
        File dir = new File(avatarLocation);
        String[] fileNames = dir.list();
        for (String name : fileNames) {
            if (name.matches(Id + ".*")) {
                File avatar = new File(avatarLocation + name);
                avatar.delete();
            }
        }
        //后缀不变，前缀改成用户名
        String[] nameSplit = file.getOriginalFilename().split("[.]");
        File newAvatar = new File(avatarLocation + Id + "." + nameSplit[nameSplit.length - 1]);
        FileOutputStream out = null;
        try{
            newAvatar.createNewFile();
            out = new FileOutputStream(newAvatar);
            out.write(file.getBytes());

        }catch (IOException e){
            e.printStackTrace();
            throw new OtherFail();
        }finally {
            if(out != null){
                try{
                    out.close();
                }catch (Exception e){}
            }
        }
    }

    @Override
    public void saveUserAvatar(String userId, MultipartFile file) throws NotExist, OtherFail {
        saveAvatar(userId, file, userAvatarLocation);
    }

    @Override
    public void saveTeamAvatar(String teamId, MultipartFile file) throws NotExist, OtherFail {
        saveAvatar(teamId, file, teamAvatarLocation);
    }

    public void writeToFile(String dir, byte[] bytes)throws OtherFail{
        FileOutputStream out = null;
        File f = new File(dir);
        try{
            out = new FileOutputStream(f);
            f.delete();
            f.createNewFile();
            out.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                out.close();
            }catch (Exception e2){}
        }
    }
    public byte[] readFromFile(String dir)throws OtherFail, FileNotFoundException{
        FileInputStream in = null;
        try{
            File avatar = new File(dir);
            in = new FileInputStream(avatar);
            byte[] ret = new byte[(int) avatar.length()];
            in.read(ret);
            in.close();
            return ret;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                in.close();
            }catch (Exception e){}
        }
        return null;
    }

    @Override
    public void saveDocument(String docId, String content) throws NotExist, OtherFail {
        if (documentDao.selectDoc(docId) == null) throw new NotExist();
        writeToFile(documentLocation + docId + "." + "html", content.getBytes(StandardCharsets.UTF_8));
    }
    @Override
    public void saveTemplate(String tempId, String content) throws NotExist, OtherFail {
        //todo template判断是否存在
        writeToFile(templateLocation + tempId + "." + "html", content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String saveDocumentImg(MultipartFile file) throws OtherFail {
        //保存为md5值
        String[] nameSplit = file.getOriginalFilename().split("[.]");
        try{
            String name = md5HSashCode(file.getInputStream()) + "." + nameSplit[nameSplit.length - 1];
            writeToFile(imageLocation + name, file.getBytes());
            return imgBaseUrl + name;
        }catch (IOException e){
            e.printStackTrace();
            throw new OtherFail();
        }
    }


    public byte[] getAvatar(String Id, String avatarLocation) throws NotExist, OtherFail {
        if(userDao.selectUser(Id) == null)throw new NotExist();
        File dir = new File(avatarLocation);
        String[] fileNames = dir.list();
        for (String name : fileNames) {
            if (name.matches(Id + ".*")) {
                try{
                    return readFromFile(avatarLocation + name);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                    throw new NotExist();
                }
            }
        }
        throw new NotExist();
    }
    @Override
    public byte[] getUserAvatar(String userId) throws NotExist, OtherFail {
        return getAvatar(userId, userAvatarLocation);
    }

    @Override
    public byte[] getTeamAvatar(String teamId) throws NotExist, OtherFail {
        return getAvatar(teamId, userAvatarLocation);
    }


    @Override
    public String getDocument(String docId) throws NotExist, OtherFail {
        if(documentDao.selectDoc(docId) == null) throw new NotExist();
        try{
            return new String(readFromFile(documentLocation + docId + ".html"));
        }catch (FileNotFoundException e){
            e.printStackTrace();
            throw new NotExist();
        }
    }
    @Override
    public String getDocSize(String docId) throws NotExist, OtherFail {
        if(documentDao.selectDoc(docId) == null) throw new NotExist();
        try{
            File file =  new File(documentLocation + docId + ".html");
            long l = file.length();
            if(l < 1024){
                return l + "B";
            }else if(l < 1024*1024){
                return l/1024.0 + "KB";
            }else{
                return l/(1024.0*1024) + "MB";
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new OtherFail();
        }
    }
    @Override
    public String getTemplate(String tempId) throws NotExist, OtherFail {
//        if(documentDao.selectDoc(docId) == null) throw new NotExist();
        //todo
        try{
            return new String(readFromFile(documentLocation + tempId + ".html"));
        }catch (FileNotFoundException e){
            e.printStackTrace();
            throw new NotExist();
        }
    }



    @Override
    public byte[] getImage(String fileName) throws NotExist, OtherFail {
        try{
            return readFromFile(imageLocation + fileName);
        }catch (FileNotFoundException e){
            throw new NotExist();
        }
    }

    public String getAvatarContentType(String Id, String avatarLocation) throws NotExist {
        File dir = new File(avatarLocation);
        String[] fileNames = dir.list();
        for (String name : fileNames) {
            if (name.matches(Id + ".*")) {
                File avatar = new File(avatarLocation + name);
                return URLConnection.guessContentTypeFromName(name);
            }
        }
        throw new NotExist();
    }
    @Override
    public String getUserAvatarContentType(String userId) throws NotExist {
        return getAvatarContentType(userId, userAvatarLocation);
    }

    @Override
    public String getTeamAvatarContentType(String teamId) throws NotExist {
        return getAvatarContentType(teamId, userAvatarLocation);
    }

    @Override
    public String getImageContentType(String fileName) {
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
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}