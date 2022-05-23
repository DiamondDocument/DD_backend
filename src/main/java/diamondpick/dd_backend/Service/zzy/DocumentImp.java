package diamondpick.dd_backend.Service.zzy;

import diamondpick.dd_backend.Dao.zzy.DocumentDao;
import diamondpick.dd_backend.Entity.zzy.Document;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class DocumentImp implements DocumentService {
    @Autowired
    DocumentDao documentDao;

    @Autowired
    FileService fileService;

    String baseLocation = "C:/Users/18389/Desktop/documents/";

    int docNum = -1;
    String generateId(){
        if(docNum == -1) docNum = documentDao.numOfDoc();
        return "d" + Integer.toString((docNum++) + 100000);
    }

    @Override
    public String newDoc(String name, String spaceId, String userId, int authority, String parentId) {
        String docId = generateId();
        try{
            documentDao.insertDoc(docId,name,userId,authority,parentId,spaceId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
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
    public ArrayList<Document> getCollection(String userId) {
        ArrayList<Document> ret = documentDao.selectCollections(userId);
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


    @Override
    public ArrayList<Document> getDocumentBySpaceId(String userspaceId) {
        return documentDao.selectDocs("space_id", userspaceId);
    }

    @Override
    public ArrayList<Document> getDocumentByParentId(String folderId) {
        return documentDao.selectDocs("parent_id", folderId);
    }

    @Override
    public Document selectDocByDocId(String msgDocId) {
        return documentDao.selectDoc(msgDocId);
    }


}
