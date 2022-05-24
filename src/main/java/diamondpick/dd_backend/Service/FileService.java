package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.*;
import diamondpick.dd_backend.Exception.NotExist.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {

    public void saveAvatar(String userId, MultipartFile file)throws UserNotExist, IOException;
    public void saveDocument(String docId, String content)throws DocNotExist, IOException;
    //返回目录
    public String saveDocumentImg(MultipartFile file)throws IOException;
    public void saveTemplate(String tempId, String content)throws TempNotExist, IOException;

    public byte[] getAvatar(String userId) throws UserNotExist, IOException;
    public String getDocument(String docId) throws DocNotExist, IOException;
    public byte[] getImage(String fileName) throws ImageNotExist, IOException;
    public String getTemplate(String tempId) throws TempNotExist, IOException;

    public String getAvatarContentType(String userId)throws UserNotExist;
    public String getImageContentType(String fileName)throws ImageNotExist;


}
