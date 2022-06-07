package diamondpick.dd_backend.ServiceImp;

import com.aspose.words.Document;
import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Dao.TemplateDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Service.LocalFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


/**存储的所有文件都存放在了程序运行时所在目录的/DD_file文件夹下*/
@Service
public class LocalFileServiceImp implements LocalFileService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private TeamDao teamDao;


//    private String baseLocation = new File("").getAbsolutePath() + "/DD_file/";
    private String baseLocation = "./DD_file/";
//    private String baseLocation = "C:/Users/18389/Desktop/DD_file/";
    private String userAvatarLocation = baseLocation + "user/avatar/";
    private String documentLocation = baseLocation + "document/";
    private String imageLocation = baseLocation + "image/";
    private String templateLocation = baseLocation + "template/content/";
    private String teamAvatarLocation = baseLocation + "team/avatar/";
    private String templateImgLocation = baseLocation + "template/image/";
    private String templateThumbnailLocation = baseLocation + "template/thumbnail/";
    private String defaultLocation = "./defaultFile/";
    private String defaultName = "_defaultavatar.webp";

    private String baseUrl = "http://43.138.71.108/api/url";
    private String download = "http://43.138.71.108/api/document/download";

    public LocalFileServiceImp() {
        if(System.getenv().get("IS_SERVICE") == null){
            baseUrl = "http://localhost/api/url/";
            download = "http://localhost/api/document/download";
        }else{
            baseUrl = "http://" + System.getenv().get("IS_SERVICE") + "/api/url";
            download = "http://" + System.getenv().get("IS_SERVICE") + "/api/document/download";
        }
        File f = new File(baseLocation);
        f.mkdirs();
        f = new File(userAvatarLocation);
        f.mkdirs();
        f = new File(documentLocation);
        f.mkdirs();
        f = new File(imageLocation);
        f.mkdirs();
        f = new File(teamAvatarLocation);
        f.mkdirs();
        f = new File(templateLocation);
        f.mkdirs();
        f = new File(templateImgLocation);
        f.mkdirs();
        f = new File(templateThumbnailLocation);
        f.mkdirs();
        try{
            Files.copy(new File(defaultLocation + "_defaultavatar.webp").toPath(), new File(userAvatarLocation + "_defaultavatar.webp").toPath());
            Files.copy(new File(defaultLocation + "_defaultavatar.webp").toPath(), new File(teamAvatarLocation + "_defaultavatar.webp").toPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveAvatar(String Id, MultipartFile file, String avatarLocation) throws  OtherFail {
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
    public void writeToFile(String dir, byte[] bytes)throws OtherFail{
        FileOutputStream out = null;
        File f = new File(dir);
        try{
            out = new FileOutputStream(f);
            f.delete();
            f.createNewFile();
            out.write(bytes);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
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
    public String getIdImageUrl(String Id, String location) throws NotExist{
        File dir = new File(location);
        String[] fileNames = dir.list();
        for (String name : fileNames) {
            if (name.matches(Id + ".*")) {
                return baseUrl + "?location=" + location + name;
            }
        }
        return baseUrl + "?location=" + location + defaultName;
    }

    @Override
    public void saveUserAvatar(String userId, MultipartFile file) throws NotExist, OtherFail {
        if(userDao.selectUser(userId) == null) throw new UserNotExist();
        saveAvatar(userId, file, userAvatarLocation);
    }

    @Override
    public void saveTeamAvatar(String teamId, MultipartFile file) throws NotExist, OtherFail {
        if(teamDao.selectTeam(teamId) == null) throw new TeamNotExist();
        saveAvatar(teamId, file, teamAvatarLocation);
    }

    @Override
    public void saveDocument(String docId, String content) throws NotExist, OtherFail {
        if (documentDao.selectDoc(docId) == null) throw new NotExist();
        writeToFile(documentLocation + docId + "." + "html", content.getBytes());
    }
    @Override
    public void saveTemplate(String tempId, String content) throws NotExist, OtherFail {
        if(templateDao.selectTemp(tempId) == null)throw new NotExist();
        writeToFile(templateLocation + tempId + "." + "html", content.getBytes(StandardCharsets.UTF_8));
        saveTemplateImg(templateLocation + tempId + "." + "html",
                templateImgLocation + tempId + ".png",
                templateThumbnailLocation + tempId + ".png");
    }

    @Override
    public String getUserAvatarUrl(String userId) throws NotExist {
        return getIdImageUrl(userId, userAvatarLocation);
    }

    @Override
    public String getTeamAvatarUrl(String teamId) throws NotExist {
        return getIdImageUrl(teamId, teamAvatarLocation);
    }

    @Override
    public List<String> getTemplateImageUrl(String tempId) throws NotExist {
        List<String> urls = new ArrayList<>();
        for(int i = 1; ;i++){
            String location = templateImgLocation + tempId + "_" + i + ".png";
            if(!new File(location).isFile()) break;
            urls.add(baseUrl + "?location=" + location);
        }
        return urls;
    }

    @Override
    public String getThumbnailUrl(String tempId) throws NotExist, OtherFail {
        return baseUrl + "?location=" + templateThumbnailLocation + tempId + ".png";
    }

    @Override
    public String saveDocumentImg(MultipartFile file) throws OtherFail {
        //保存为md5值
        String[] nameSplit = file.getOriginalFilename().split("[.]");
        try{
            String name = md5HSashCode(file.getInputStream()) + "." + nameSplit[nameSplit.length - 1];
            writeToFile(imageLocation + name, file.getBytes());
            return baseUrl + "?location=" + imageLocation + name;
        }catch (IOException e){
            e.printStackTrace();
            throw new OtherFail();
        }
    }

    public byte[] getAvatar(String Id, String avatarLocation) throws NotExist, OtherFail {
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
    public String getContentTypeByPath(String path) {
        File file = new File(path);
        return URLConnection.guessContentTypeFromName(file.getName());
    }

    @Override
    public byte[] getByLocation(String path) {
        try{
            return readFromFile(path);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new byte[0];
    }



    @Override
    public String getTemplate(String tempId) throws NotExist, OtherFail {
        if(templateDao.selectTemp(tempId) == null) throw new NotExist();
        try{
            return new String(readFromFile(documentLocation + tempId + ".html"));
        }catch (FileNotFoundException e){
            e.printStackTrace();
            throw new NotExist();
        }
    }



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
    public String getUserAvatarContentType(String userId) throws NotExist {
        return getAvatarContentType(userId, userAvatarLocation);
    }

    public String getTeamAvatarContentType(String teamId) throws NotExist {
        return getAvatarContentType(teamId, userAvatarLocation);
    }

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

    /**
     * @param htmlLocation
     * @param img png文件名
     * @param thumbnail png文件名
     */
    public void saveTemplateImg(String htmlLocation, String img, String thumbnail){
//        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//        Dimension dimension = new Dimension();
//        dimension.width = 500;
//        imageGenerator.setSize(dimension);
//        imageGenerator.loadHtml(html);
//        imageGenerator.saveAsImage(img);
//        dimension.width = 100;
//        dimension.height = (int)(100 * 1.414);
//        imageGenerator.saveAsImage(thumbnail);
        try{
//            HTMLDocument doc = new HTMLDocument(htmlLocation);
            Document doc = new Document(htmlLocation);
            for (int page = 0; page < doc.getPageCount(); page++)
            {
                Document extractedPage = doc.extractPages(page, 1);
                String splitedImg = img.split("[.]png")[0];
                extractedPage.save(String.format(splitedImg + "_%d.png", page + 1));
            }
            doc = doc.extractPages(0, 1);
            doc.save(thumbnail);
//            com.aspose.html.saving.ImageSaveOptions options = new ImageSaveOptions(ImageFormat.Jpeg);
//            Converter.convertHTML(doc, options, img.getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void htmlToDocx(String docId) throws NotExist, OtherFail {
        Document doc = null;
        try{
            doc = new Document(documentLocation + docId + ".html");
        }catch (Exception e){
            throw new NotExist();
        }
        try {
            doc.save(baseLocation + "exportTmp.docx");
        }catch (Exception e){
            throw new OtherFail();
        }
    }

    @Override
    public byte[] getDocx() {
        return getByLocation(baseLocation + "exportTmp.docx") ;
    }

    @Override
    public String docxToHtml(MultipartFile file) throws OperationFail {
        Document doc = null;
        try{
            File f = new File(baseLocation + "importTmp.docx");
            f.createNewFile();
            FileOutputStream os = new FileOutputStream(f);
            os.write(file.getBytes());
            doc = new Document(baseLocation + "importTmp.docx");
            os.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFail();
        }
        try {
            doc.save( baseLocation + "importTmp.html");
            return new String(readFromFile("importTmp.html"));
        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFail();
        }
    }

    @Override
    public String getDownloadUrl() {
        return download;
    }
}