package diamondpick.dd_backend.Old.lyz.lyz;

/*
@Service
public class SpaceServiceImpl implements SpaceService {
    @Autowired
    private SpaceDao spaceDao;

    @Autowired
    private FolderDao folderDao;

    @Autowired
    private DocumentDao documentDao;

    @Override
    public UserSpace newUserSpace(String userId, String userSpaceName) {
        try {
            UserSpace space = new UserSpace(userSpaceName, userId);
            spaceDao.newUserSpace(space);
            return space;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TeamSpace newTeamSpace(String teamId, String teamSpaceName) {
        try {
            TeamSpace space = new TeamSpace(teamSpaceName, teamId);
            spaceDao.newTeamSpace(space);
            return space;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserRecycle newUserRecycle(String fileId, String userId, String delId) {
//        try {
//            String preFolderId = null;
//            if (fileId.charAt(0) == 'f') {
//                preFolderId = folderDao.getParentId(fileId);
//            } else if (fileId.charAt(0) == 'd') {
//                preFolderId = documentDao.getParentId(fileId);
//            }
//            UserRecycle recycle = new UserRecycle(fileId, delId, preFolderId, userId);
//            spaceDao.newUserRecycle(recycle);
//            return recycle;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    @Override
    public TeamRecycle newTeamRecycle(String fileId, String teamId, String delId) {
        try {
            String preFolderId = null;
            if (fileId.charAt(0) == 'f') {
                preFolderId = folderDao.getParentId(fileId);
            } else if (fileId.charAt(0) == 'd') {
                preFolderId = documentDao.selectDoc(fileId).getParentId();
            }
            TeamRecycle recycle = new TeamRecycle(fileId, delId, preFolderId, teamId);
            spaceDao.newTeamRecycle(recycle);
            return recycle;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getSpaceIdByTeamId(String teamId) {
        return null;
    }

    @Override
    public String getSpaceIdByUserId(String userId) {
        return null;
    }

    @Override
    public UserSpace getUserSpace(String userId) {
        return null;
    }

    @Override
    public ArrayList<User> getSpaceOwner(String spaceId) throws SpaceNotExist {
        return null;
    }
}


 */