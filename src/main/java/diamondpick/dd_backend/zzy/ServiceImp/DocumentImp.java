package diamondpick.dd_backend.zzy.ServiceImp;

import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Exception.*;
import diamondpick.dd_backend.Service.SpaceService;
import diamondpick.dd_backend.Service.UserService;
import diamondpick.dd_backend.zzy.Dao.CollectionDao;
import diamondpick.dd_backend.zzy.Dao.DocumentDao;
import diamondpick.dd_backend.zzy.Entity.Document;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.FileService;
import diamondpick.dd_backend.zzy.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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



    @Override
    public String getSize(String docId) throws DocNotExist {
        String content;
        try{
            content = fileService.getDocument(docId);
            if(content.length() < 1024){
                return content.length() + "B";
            }else if(content.length() < 1024*1024){
                return content.length() / 1024.0 + "K";
            }else {
                return content.length() / (1024.0*1024) + "M";
            }
        }catch (IOException e){
            e.printStackTrace();
            return "NAN";
        }
    }

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
            fileService.saveDocument(docId, "");
        }catch (Exception e){
            e.printStackTrace();
            throw new CreateFail();
        }
        return docId;
    }

    @Override
    public String newDocByTemplate(String name, String spaceId, String userId, int authority, String parentId, String tempId) throws CreateFail {
        String docId = generateId();
        try{
            documentDao.insertDoc(docId,name,userId,authority,parentId,spaceId);
            fileService.saveDocument(docId,fileService.getTemplate(tempId));
        }catch (Exception e){
            e.printStackTrace();
            throw new CreateFail();
        }
        return docId;
    }

    @Override
    public ArrayList<Document> getCollection(String userId) throws UserNotExist {
        userService.selectUserByUserId(userId);
        ArrayList<Document> ret = collectionDao.selectCollections(userId);
        return ret;
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
    public Document selectDocByDocId(String DocId)throws DocNotExist {
        Document ret = documentDao.selectDoc(DocId);
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
    @Override
    public void collect(String userId, String docId)throws UserNotExist, DocNotExist, AlreadyCollect, NoAuth {
        selectDocByDocId(docId);
        userService.selectUserByUserId(userId);
        if(collectionDao.selectCollection(docId, userId) != null) throw new AlreadyCollect();
        if(checkAuth(docId, userId) < 2){
            throw new NoAuth();
        }
        try {
            collectionDao.insertCollection(docId,userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void discollect(String userId, String docId)throws UserNotExist, DocNotExist, NotyetCollect {
        selectDocByDocId(docId);
        userService.selectUserByUserId(userId);
        if(collectionDao.selectCollection(docId, userId) == null) throw new NotyetCollect();
        try {
            collectionDao.deleteCollection(docId,userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
