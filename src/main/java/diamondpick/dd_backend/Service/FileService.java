package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.Exception.OtherFail;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {

    public void saveUserAvatar(String userId, MultipartFile file)throws NotExist, OtherFail;
    public void saveTeamAvatar(String teamId, MultipartFile file)throws NotExist, OtherFail;
    public void saveDocument(String docId, String content)throws NotExist, OtherFail;
    //返回目录
    public String saveDocumentImg(MultipartFile file)throws OtherFail;
    public void saveTemplate(String tempId, String content)throws NotExist, OtherFail;

    public byte[] getUserAvatar(String userId) throws NotExist, OtherFail;
    public byte[] getTeamAvatar(String teamId) throws NotExist, OtherFail;
    public String getDocument(String docId) throws NotExist, OtherFail;
    public byte[] getImage(String fileName) throws NotExist, OtherFail;
    public String getTemplate(String tempId) throws NotExist, OtherFail;

    public String getUserAvatarContentType(String userId)throws NotExist;
    public String getTeamAvatarContentType(String teamId)throws NotExist;
    public String getImageContentType(String fileName)throws NotExist;


}
