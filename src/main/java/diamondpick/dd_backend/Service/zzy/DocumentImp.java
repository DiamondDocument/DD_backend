package diamondpick.dd_backend.Service.zzy;

import diamondpick.dd_backend.Dao.zzy.DocumentDao;
import diamondpick.dd_backend.Entity.zzy.Document;
import diamondpick.dd_backend.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DocumentImp implements DocumentService {
    @Autowired
    DocumentDao documentDao;
    int docNum = documentDao.numOfDoc();
    String generateId(){
        return "d" + Integer.toString((docNum++) + 100000);
    }
//    @Override
//    public Document newDoc(String name, String userId, int authority, String fatherId) {
////        String id = "d" + (documentDao.numOfDoc() + 100000) ;
////        Document newDoc = new Document();
////        newDoc.setName(name);
////        newDoc.setId(id);
//////        newDoc.setCreatorUId(userId);
////        User user = new User();
////        user.setUserId(userId);
////        newDoc.setCreator(user);
////        newDoc.setCreateTime(new Date());
//////        newDoc.setFatherId(fatherId);
////        newDoc.setNowAuthority(authority);
////        newDoc.setSelfAuthority(authority);
//        try{
////            documentDao.insertDoc(newDoc,"123");
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//        return null;
//    }

    @Override
    public String newDoc(String name, String spaceId, String userId, int authority, String parentId) {
        String docId = generateId();
        try{
            documentDao.insertDoc(docId,name,userId,authority,parentId,spaceId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return docId;
    }

    @Override
    public ArrayList<Document> getCollection(String userId) {
        ArrayList<Document> ret = documentDao.selectCollection(userId);
        return ret;
    }

    @Override
    public String getSize(String docId) {
        return null;
    }

    @Override
    public int collect(String userId, String docId) {
        if(documentDao.selectDoc(docId) == null) return -1;
        if(documentDao.selectCollection(docId, userId) != null) return 1;
        if(getAuth(docId, userId) < 2){
            return 2;
        }
        try {
            documentDao.insertCollection(docId,userId);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    @Override
    public int discollect(String userId, String docId) {
        if(documentDao.selectDoc(docId) == null) return -1;
        if(documentDao.selectCollection(docId, userId) == null) return 1;
        if(getAuth(docId, userId) < 2){
            return -1;
        }
        try {
            documentDao.deleteCollection(docId,userId);
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
}
