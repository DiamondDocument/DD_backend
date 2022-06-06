package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Comment;
import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Exception.*;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.Document.SomoneEditing;
import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.Entity.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public interface DocumentService {

    /**
     * @param file 如果是按本地.docx文件创建则需要，不是的话为null
     * @return 返回新建文档的id
     */
    public String newDoc(String name, String spaceId, String userId, String parentId, MultipartFile file)throws OperationFail;
    /**
     * @return 返回新建文档的id
     */
    public String newDocByTemplate(String name, String spaceId, String userId, String parentId, String tempId)throws OperationFail;

    /**
     * @return 返回一个形式化的字符串，如"98K"或者"1.2M"
     */
    public String getSize(String docId) throws OperationFail;

    public void collect(String userId, String docId)throws  AlreadyCollect, NoAuth, OtherFail;

    public void disCollect(String userId, String docId)throws  NotyetCollect, OtherFail;

    /**
     * @return 返回1到4中的一种
     */
    public int checkShare(String docId)throws OperationFail;

    public void keepEdit(String userId, String docId)throws NoAuth, SomoneEditing, OtherFail;

    public void quitEdit(String userId, String docId)throws OperationFail ;

    public void share(String docId, int auth)throws OperationFail;


    public void disShare(String docId)throws OperationFail;

    /**不用查权限了*/
    public List<Comment> getComment(String docId)throws OperationFail;

    public void newComment(String content, String docId, String creatorId)throws NoAuth, OtherFail;

    public void deleteComment(int commentId, String userId)throws NoAuth, OtherFail;

}
