package diamondpick.dd_backend.zzy;

import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Old.zzy.Dao.CollectionDao;
import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.Date;
import java.util.List;

@SpringBootTest
class DdBackendApplicationTests {

    @Autowired
    DocumentDao documentDao;
    @Autowired
    CollectionDao collectionDao;

    @Autowired
    DocumentService documentService;

    @Autowired
    UserService userService;
    @Test
    void selectDoc(){
        Document doc = documentDao.selectDoc("d100000");
        try{
            documentDao.insertCollection("1", "123");
        }catch (DuplicateKeyException e){}
        try{
            documentDao.insertRecent("1", "123");
        }catch (DuplicateKeyException e){
            documentDao.updateRecent("1", "123");
        }
        try{
            documentDao.insertRecent("1", "123");
        }catch (DuplicateKeyException e){
            documentDao.updateRecent("1", "123");
        }
        documentDao.updateDoc("123", "is_delete", false);
        List<Document> docs;
        docs = documentDao.selectRootDir("user", "1");
        docs = documentDao.selectDeleted("user", "1");
        docs = documentDao.selectCollection("1");
        documentDao.updateToDelete("123", "1");
        docs = documentDao.selectRootDir("user","1");
        docs = documentDao.selectDeleted("user", "1");
        docs = documentDao.selectCollection("1");
    }
}
