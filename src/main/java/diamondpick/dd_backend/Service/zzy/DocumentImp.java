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
    public Document newDoc(String name, String spaceId, String userId, int authority, String parentId) {
        return null;
    }

    @Override
    public ArrayList<Document> getCollection(String userId) {
//        ArrayList<Document> ret = documentDao.selectByCollector(userId);
//        return ret;
        return null;
    }

    @Override
    public String getSize(String docId) {
        return null;
    }

    @Override
    public boolean collect(String userId, String documentId) {
//        try {
//            documentDao.insertDocCollector(documentId,userId);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
        return false;
    }

//    @Override
//    public String getCreatorId(String documentId) {
//        try {
////            String ret = documentDao.findUIdByDId(documentId);
////            return ret;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//        return null;
//    }
}
