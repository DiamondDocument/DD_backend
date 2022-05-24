package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.*;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.zzy.Entity.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface DocumentService {

    public String newDoc(String name, String spaceId, String userId, int authority, String parentId)throws OperationFail;
    public String newDocByTemplate(String name, String spaceId, String userId, int authority, String parentId, String tempId)throws OperationFail;

    public ArrayList<Document> getCollection(String userId)throws UserNotExist;

    /**
     * @return 返回一个形式化的字符串，如"98K"或者"1.2M"
     */
    public String getSize(String docId) throws DocNotExist;

    public void collect(String userId, String docId)throws UserNotExist, DocNotExist, AlreadyCollect, NoAuth;

    public void discollect(String userId, String docId)throws UserNotExist, DocNotExist, NotyetCollect;

    ArrayList<Document> getDocumentBySpaceId(String userspaceId)throws NotExist;

    ArrayList<Document> getDocumentByParentId(String folderId)throws NotExist;

    Document selectDocByDocId(String DocId) throws DocNotExist;

    int checkAuth(String docId, String userId)throws DocNotExist, UserNotExist;
}
