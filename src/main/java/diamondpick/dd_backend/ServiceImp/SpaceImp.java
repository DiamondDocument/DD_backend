package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.FolderDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceImp implements SpaceService {
    @Autowired
    private FolderDao folderDao;

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private AuthService authService;

    @Override
    public List<File> getUserSpace(String userId, String visitorId) throws OperationFail {
        List<File> files = new ArrayList<>();
        try {
            List<Folder> folders = folderDao.selectRootDir("user", userId);
            List<Document> documents = documentDao.selectRootDir("user", userId);
            if (userId.equals(visitorId)) {
                files.addAll(folders);
                files.addAll(documents);
            } else {
                for (Folder folder : folders) {
                    if (authService.checkFileAuth(folder.getFileId(), visitorId) >= 2) {
                        files.add(folder);
                    }
                }
                for (Document document : documents) {
                    if (authService.checkFileAuth(document.getFileId(), visitorId) >= 2) {
                        files.add(document);
                    }
                }
            }
        } catch (Exception e) {
            throw new OperationFail();
        }
        return files;
    }

    @Override
    public List<File> getTeamSpace(String teamId, String visitorId) throws OperationFail {
        List<File> files = new ArrayList<>();
        String userId = teamDao.selectTeam(teamId).getCaptainId();
        try {
            List<Folder> folders = folderDao.selectRootDir("team", userId);
            List<Document> documents = documentDao.selectRootDir("team", userId);
            if (teamId.equals(visitorId)) {
                files.addAll(folders);
                files.addAll(documents);
            } else {
                for (Folder folder : folders) {
                    if (authService.checkFileAuth(folder.getFileId(), visitorId) >= 2) {
                        files.add(folder);
                    }
                }
                for (Document document : documents) {
                    if (authService.checkFileAuth(document.getFileId(), visitorId) >= 2) {
                        files.add(document);
                    }
                }
            }
        } catch (Exception e) {
            throw new OperationFail();
        }
        return files;
    }

    @Override
    public List<File> getUserRecycle(String userId) throws OperationFail {
        List<File> files = new ArrayList<>();
        try {
            List<Folder> folders = folderDao.selectDeleted("user", userId);
            List<Document> documents = documentDao.selectDeleted("user", userId);
            files.addAll(folders);
            files.addAll(documents);
        } catch (Exception e) {
            throw new OperationFail();
        }
        return files;
    }

    @Override
    public List<File> getTeamRecycle(String teamId) throws OperationFail {
        List<File> files = new ArrayList<>();
        String userId = teamDao.selectTeam(teamId).getCaptainId();
        try {
            List<Folder> folders = folderDao.selectDeleted("team", userId);
            List<Document> documents = documentDao.selectDeleted("team", userId);
            files.addAll(folders);
            files.addAll(documents);
        } catch (Exception e) {
            throw new OperationFail();
        }
        return files;
    }

    @Override
    public List<File> getRecentBrowser(String userId) throws OperationFail {
        List<File> files = new ArrayList<>();
        try {
            List<Document> documents = documentDao.selectRecent(userId, 10);
            for (Document document : documents) {
                if (authService.checkFileAuth(document.getFileId(), userId) >= 2) {
                    files.add(document);
                }
            }
        } catch (Exception e) {
            throw new OperationFail();
        }
        return files;
    }

    @Override
    public List<File> getCollection(String userId, String visitorId) throws OperationFail {
        List<File> files = new ArrayList<>();
        try {
            List<Document> documents = documentDao.selectCollection(userId);
            if (userId.equals(visitorId)) {
                files.addAll(documents);
            } else {
                for (Document document : documents) {
                    if (authService.checkFileAuth(document.getFileId(), visitorId) >= 2) {
                        files.add(document);
                    }
                }
            }
        } catch (Exception e) {
            throw new OperationFail();
        }
        return files;
    }
}
