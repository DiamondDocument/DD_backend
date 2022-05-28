package diamondpick.dd_backend.Old.lyz.lyz;

//@Service
//public class FolderServiceImpl implements FolderService {
//    @Autowired
//    private FolderDao folderDao;
//
//    @Override
//    public String newFolder(String folderName, String creatorId, String parentId, String spaceId, int auth) {
//        Folder folder;
//        try {
//            folder = new Folder(folderName, creatorId, new Date(System.currentTimeMillis()), parentId, spaceId, auth, auth);
//            folderDao.newFolder(folder);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return folder.getFolderId();
//    }
//
//    @Override
//    public ArrayList<Folder> openFolder(String folderId) {
//        return folderDao.openFolder(folderId);
//    }
//
//    @Override
//    public ArrayList<Folder> getFolderBySpaceId(String spaceId) {
//        return folderDao.getFolderBySpaceId(spaceId);
//    }
//
//    @Override
//    public ArrayList<Folder> getFolderByParentId(String parentId) {
//        return folderDao.getFolderByParentId(parentId);
//    }
//
//
//}
