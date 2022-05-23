package diamondpick.dd_backend.zzy;

import diamondpick.dd_backend.Dao.zzy.CollectionDao;
import diamondpick.dd_backend.Dao.zzy.DocumentDao;
import diamondpick.dd_backend.Entity.zzy.Document;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;

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
    void updateDoc(){
//        documentDao.insertDoc("123", "123", "123", 2, "123");
        documentDao.updateDoc("123", "modify_time", new Date());
    }
//    @Test
//    void collect(){
//        try{
//            documentDao.insertDoc("124", "123", "123", 2, "123");
//            documentDao.insertDoc("125", "123", "123", 2, "123");
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
////        collectionDao.insertCollection("123","123");
////        collectionDao.insertCollection("124","123");
////        collectionDao.insertCollection("125","123");
//        ArrayList<Document> ret = collectionDao.selectCollection("123");
//    }
//    void addUser(){
//        User user = new User();
//        user.setUserId("123");
//        user.setPassword("123");
//        userService.addUser(user);
//    }

//    @Test
//    void insertDoc() {
////        addUser();
////        String id = "d100000";
////        Document newDoc = new Document();
////        newDoc.setId(id);
////        newDoc.setCreateTime(new Date());
////        newDoc.setNowAuthority(4);
////        newDoc.setSelfAuthority(4);
////        Document retDoc;
////        documentDao.insertDoc("空文档","123",3,null);
////        retDoc = newDoc;
//    }
//    @Test
//    void NewDocument(){
//        Document doc;
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//    }
//    @Test
//    void getCreatorId(){
//        String uid;
//        uid = documentService.getCreatorId("d100000");
//        uid = documentService.getCreatorId("d100001");
//        uid = documentService.getCreatorId("d10000123");
//        uid = documentService.getCreatorId("d100005");
//    }
//    @Test
//    void Collector(){
//
//        Document doc;
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//        documentService.collect("123", "d100000");
//        documentService.collect("123", "d100001");
//        documentService.collect("123", "d100002");
//        documentService.collect("123", "d100003");
//        ArrayList<Document> arr;
//        arr = documentService.getCollection("123");
//        return;
//    }
//    @Test
//    void CreateDocument(){
//        User user = new User();
//        user.setUserId("123");
//        user.setPassword("123");
//        userService.addUser(user);
//        Document doc;
//        doc = documentService.newDoc("空文件","123",2,"f123456");
//        doc = documentService.newDoc("空文件","123",2,"f123456");
//        doc = documentService.newDoc("空文件","123",2,"f123456");
//        doc = documentService.newDoc("空文件","zzy",2,"f123456");
//    }
//    @Test
//    void selectDoc(){
//        User user = new User();
//        user.setUserId("123");
//        user.setPassword("123");
//        userService.addUser(user);
//        Document doc;
//        doc = documentService.newDoc("空文件","123",2,"f123456");
//        Document ret;
//        ret = documentDao.selectDoc("d100000");
//    }
}
