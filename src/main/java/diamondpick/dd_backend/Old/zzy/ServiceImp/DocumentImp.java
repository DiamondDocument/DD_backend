package diamondpick.dd_backend.Old.zzy.ServiceImp;

/*
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
    public String newDoc(String name, String spaceId, String userId, int authority, String parentId)throws OperationFail {
        String docId = generateId();
        try{
            documentDao.insertDoc(docId,name,userId,authority,parentId,spaceId);
            fileService.saveDocument(docId, "");
        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFail();
        }
        return docId;
    }

    @Override
    public String newDocByTemplate(String name, String spaceId, String userId, int authority, String parentId, String tempId) throws OperationFail {
        String docId = generateId();
        try{
            documentDao.insertDoc(docId,name,userId,authority,parentId,spaceId);
            fileService.saveDocument(docId,fileService.getTemplate(tempId));
        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFail();
        }
        return docId;
    }

    @Override
    public List<Document> getRootDocInSpace(String spaceId) throws SpaceNotExist {
        return null;
    }

    @Override
    public List<Folder> getDocInFolder(String folderId) throws FolderNotExist {
        return null;
    }

    @Override
    public ArrayList<Document> getCollection(String userId) throws UserNotExist {
        userService.selectUserByUserId(userId);
        ArrayList<Document> ret = collectionDao.selectCollections(userId);
        return ret;
    }

//    @Override
//    public List<Document> getDocumentBySpaceId(String userspaceId) {
//        return documentDao.selectDocBy("space_id", userspaceId);
//    }

    @Override
    public ArrayList<Document> getDocumentByFId(String folderId) throws FolderNotExist {
        return null;
    }

//    @Override
//    public List<Document> getDocumentByParentId(String folderId) {
//        return documentDao.selectDocBy("parent_id", folderId);
//    }

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
                if(u.getUserId().equals(userId)){
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
    public void changeAuth(String docId, int newAuth) throws OperationFail {

    }

    @Override
    public List<Document> getDocumentByParentId(String folderId) {
        return null;
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

    @Override
    public ArrayList<Document> getDocumentBySpaceId(String userspaceId) throws SpaceNotExist {
        return null;
    }


}


 */