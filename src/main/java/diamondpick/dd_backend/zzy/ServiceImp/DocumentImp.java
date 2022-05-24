package diamondpick.dd_backend.zzy.ServiceImp;

import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Service.SpaceService;
import diamondpick.dd_backend.Service.UserService;
import diamondpick.dd_backend.zzy.Dao.CollectionDao;
import diamondpick.dd_backend.zzy.Dao.DocumentDao;
import diamondpick.dd_backend.zzy.Entity.Document;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.FileService;
import diamondpick.dd_backend.zzy.Exception.CreateFail;
import diamondpick.dd_backend.zzy.Exception.DocNotExist;
import diamondpick.dd_backend.zzy.Exception.UserNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

@Service
public class DocumentImp implements DocumentService {
    @Autowired
    DocumentDao documentDao;
    @Autowired
    CollectionDao collectionDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;
    @Autowired
    SpaceService spaceService;

    private String baseLocation = "C:/Users/18389/Desktop/documents/";

    int docNum = -1;
    String generateId(){
        if(docNum == -1) docNum = documentDao.numOfDoc();
        return "d" + ((docNum++) + 100000);
    }

    @Override
    public String newDoc(String name, String spaceId, String userId, int authority, String parentId)throws CreateFail {
        String docId = generateId();
        try{
            documentDao.insertDoc(docId,name,userId,authority,parentId,spaceId);
        }catch (Exception e){
            e.printStackTrace();
            throw new CreateFail();
        }
        File newDocFile = new File(baseLocation + docId + ".html");
        if(newDocFile.exists() && newDocFile.isFile()) newDocFile.delete();
        try{
            newDocFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
            documentDao.deleteDoc(docId);
            return null;
        }
        return docId;
    }

    @Override
    public String newDocByTemplate(String name, String spaceId, String userId, int authority, String parentId, String tempId) throws CreateFail {
        return null;
    }


    @Override
    public ArrayList<Document> getCollection(String userId) {
        ArrayList<Document> ret = collectionDao.selectCollections(userId);
        return ret;
    }

    @Override
    public String getSize(String docId) {

        return null;
    }

    @Override
    public int collect(String userId, String docId) {
        if(documentDao.selectDoc(docId) == null) return -1;
        if(collectionDao.selectCollection(docId, userId) != null) return 1;
        if(getAuth(docId, userId) < 2){
            return 2;
        }
        try {
            collectionDao.insertCollection(docId,userId);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    @Override
    public int discollect(String userId, String docId) {
        if(documentDao.selectDoc(docId) == null) return -1;
        if(collectionDao.selectCollection(docId, userId) == null) return 1;
        if(getAuth(docId, userId) < 2){
            return -1;
        }
        try {
            collectionDao.deleteCollection(docId,userId);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int getAuth(String docId, String userId){
        Document doc = documentDao.selectDoc(docId);
        if(doc.getCreatorId().equals(userId)) return 5;
        if(doc == null)return -1;
        return doc.getNowAuth();
    }


    @Override
    public ArrayList<Document> getDocumentBySpaceId(String userspaceId) {
        return documentDao.selectDocs("space_id", userspaceId);
    }

    @Override
    public ArrayList<Document> getDocumentByParentId(String folderId) {
        return documentDao.selectDocs("parent_id", folderId);
    }

    @Override
    public Document selectDocByDocId(String msgDocId)throws DocNotExist {
        Document ret = documentDao.selectDoc(msgDocId);
        if(ret == null){
            throw new DocNotExist();
        }
        return ret;
    }

    @Override
    public int checkAuth(String docId, String userId) throws DocNotExist, UserNotExist {
        Document doc = documentDao.selectDoc(docId);
        if(doc == null){
            throw new DocNotExist();
        }
        if(userService.selectUserByUserId(userId) == null){
            throw new UserNotExist();
        }
        //先判断是否为作者
        if(doc.getCreatorId().equals(userId)) return 5;
        //再找到space对应团队中的成员
        try{
            ArrayList<User> users = spaceService.getSpaceOwner(doc.getSpaceId());
            for(User u : users){
                if(u.getUser_id().equals(userId)){
                    return 5;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //最后说明userId并不是文档的所属者
        return doc.getNowAuth();
    }


}
