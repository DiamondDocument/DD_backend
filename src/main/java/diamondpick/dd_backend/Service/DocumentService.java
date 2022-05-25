package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.Folder;
import diamondpick.dd_backend.Exception.*;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.zzy.Entity.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface DocumentService {

    public String newDoc(String name, String spaceId, String userId, int authority, String parentId)throws OperationFail;
    public String newDocByTemplate(String name, String spaceId, String userId, int authority, String parentId, String tempId)throws OperationFail;

    public List<Document> getRootDocInSpace(String spaceId)throws SpaceNotExist;
    public List<Folder> getDocInFolder(String folderId)throws FolderNotExist;


    public ArrayList<Document> getCollection(String userId)throws UserNotExist;

    /**
     * @return 返回一个形式化的字符串，如"98K"或者"1.2M"
     */
    public String getSize(String docId) throws DocNotExist;

    public void collect(String userId, String docId)throws UserNotExist, DocNotExist, AlreadyCollect, NoAuth;

    public void discollect(String userId, String docId)throws UserNotExist, DocNotExist, NotyetCollect;

    public ArrayList<Document> getDocumentBySpaceId(String userspaceId)throws SpaceNotExist;

    public ArrayList<Document> getDocumentByFId(String folderId)throws FolderNotExist;

    public Document selectDocByDocId(String DocId) throws DocNotExist;

    public int checkAuth(String docId, String userId)throws DocNotExist, UserNotExist;

    public void changeAuth(String docId, int newAuth)throws OperationFail;

}
