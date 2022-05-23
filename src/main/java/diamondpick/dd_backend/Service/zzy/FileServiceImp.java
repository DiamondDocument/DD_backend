package diamondpick.dd_backend.Service.zzy;

import diamondpick.dd_backend.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Service
public class FileServiceImp implements FileService {
    static String FileRoot = "D:\\BUAA\\2down\\soft\\big_work";
    static String AvatarRoot = "D:\\BUAA\\2down\\soft\\big_work";



    public byte[] getFile(String path){
        FileInputStream stream = null;
        try{
            File file = new File(path);
            stream = new FileInputStream(file);
            byte[] b = new byte[(int)file.length()];
            stream.read(b);
            return b;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(stream != null){
                try{
                    stream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }



    public boolean saveFile(String directory, MultipartFile file){
        FileOutputStream os = null;
        try{
            byte[] b = file.getBytes();
            File localFile = new File(directory);
            if(localFile.isFile()){
                localFile.delete();
            }
            localFile.createNewFile();
            os = new FileOutputStream(localFile);
            os.write(b);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(os != null){
                try{
                    os.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
