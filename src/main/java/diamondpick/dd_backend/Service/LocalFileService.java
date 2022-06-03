package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.Exception.OtherFail;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface LocalFileService {

    public void saveUserAvatar(String userId, MultipartFile file)throws NotExist, OtherFail;
    public void saveTeamAvatar(String teamId, MultipartFile file)throws NotExist, OtherFail;
    public void saveDocument(String docId, String content)throws NotExist, OtherFail;
    /**返回url*/
    public String saveDocumentImg(MultipartFile file)throws OtherFail;

    public void saveTemplate(String tempId, String content)throws NotExist, OtherFail;


    public String getUserAvatarUrl(String userId) throws NotExist, OtherFail;
    public String getTeamAvatarUrl(String teamId) throws NotExist, OtherFail;
    public String getTemplateImageUrl(String tempId) throws NotExist, OtherFail;
    public String getThumbnailUrl(String tempId) throws NotExist, OtherFail;

    public String getDocument(String docId) throws NotExist, OtherFail;
    public String getTemplate(String tempId) throws NotExist, OtherFail;
    public String getDocSize(String docId) throws NotExist, OtherFail;



    public String getContentTypeByPath(String location);
    public byte[] getByLocation(String location);
}
