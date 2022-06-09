package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.FolderDao;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.FileService;
import diamondpick.dd_backend.Tool.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileImp implements FileService {
    @Autowired
    private FolderDao folderDao;

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public String newFolder(String folderName, String creatorId, String parentId, String spaceId) throws OtherFail, NoAuth {
        String folderId;
        try {
            folderId = idGenerator.generateId('f');
            if (parentId == null) {
                folderDao.insertFolder(folderId, folderName, creatorId, null, spaceId);
            } else {
                if (authService.checkFileAuth(parentId, creatorId) < 4) {
                    throw new NoAuth();
                }
                folderDao.insertFolder(folderId, folderName, creatorId, parentId, spaceId);
            }
        } catch (Exception e) {
            throw new OtherFail();
        }

        return folderId;
    }

    @Override
    public List<File> openFolder(String folderId, String visitorId) throws OtherFail, NoAuth {
        List<File> files = new ArrayList<>();
        try {
            List<Folder> folders = folderDao.selectSubDir(folderId);
            List<Document> documents = documentDao.selectSubDir(folderId);
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
        } catch (Exception e) {
            throw new OtherFail();
        }
        return files;
    }

    @Override
    public void moveFile(String fileId, String newParentId) throws OtherFail, NoAuth {
        try {
            if (fileId.charAt(0) == 'f') {
                folderDao.updateFolder(fileId, "parent_id", newParentId);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateDoc(fileId, "parent_id", newParentId);
            } else {
                throw new OtherFail();
            }
        } catch (Exception e) {
            throw new OtherFail();
        }
    }

    @Override
    public int deleteFile(String fileId, String userId) throws OtherFail {
        try {
            if (authService.checkFileAuth(fileId, userId) < 4) {
                return 1;
            }
            if (fileId.charAt(0) == 'f') {
                List<Folder> folders = folderDao.selectSubDir(fileId);
                List<Document> documents = documentDao.selectSubDir(fileId);
                if (folders.size() == 0 && documents.size() == 0) {
                    folderDao.updateToDelete(fileId, userId);
                    return 0;
                }
                for (Folder folder : folders) {
                    if (deleteFile(folder.getFileId(), userId) == 1) {
                        return 1;
                    }
                    folderDao.updateToDelete(folder.getFileId(), userId);
                }
                for (Document document : documents) {
                    if (authService.checkFileAuth(document.getFileId(), userId) < 4) {
                        return 1;
                    }
                    documentDao.updateToDelete(document.getFileId(), userId);
                }
                folderDao.updateToDelete(fileId, userId);
                return 0;
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateToDelete(fileId, userId);
                return 0;
            } else {
                throw new OtherFail();
            }
        } catch (Exception e) {
            throw new OtherFail();
        }
    }

    @Override
    public void recoverFile(String fileId) throws OtherFail {
        try {
            if (fileId.charAt(0) == 'f') {
                List<Folder> folders = folderDao.selectAllSubDir(fileId);
                List<Document> documents = documentDao.selectAllSubDir(fileId);
                if (folders.size() == 0 && documents.size() == 0) {
                    folderDao.updateFolder(fileId, "is_delete", "0");
                    return;
                }
                for (Folder folder : folders) {
                    recoverFile(folder.getFileId());
                    folderDao.updateFolder(folder.getFileId(), "is_delete", "0");
                }
                for (Document document : documents) {
                    documentDao.updateDoc(document.getFileId(), "is_delete", "0");
                }
                folderDao.updateFolder(fileId, "is_delete", "0");
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateDoc(fileId, "is_delete", "0");
            } else {
                throw new OtherFail();
            }
        } catch (Exception e) {
            throw new OtherFail();
        }
    }

    @Override
    public int deletePermanently(String fileId, String userId) throws OtherFail, NoAuth {
        try {
            if (authService.checkFileAuth(fileId, userId) < 4) {
                return 1;
            }
            if (fileId.charAt(0) == 'f') {
                List<Folder> folders = folderDao.selectAllSubDir(fileId);
                List<Document> documents = documentDao.selectAllSubDir(fileId);
                System.out.println(folders);
                System.out.println(documents);
                for (Folder folder : folders) {
                    if (deletePermanently(folder.getFileId(), userId) == 1) {
                        return 1;
                    }
                    folderDao.deleteFolder(folder.getFileId());
                }
                for (Document document : documents) {
                    if (authService.checkFileAuth(document.getFileId(), userId) < 4) {
                        return 1;
                    }
                    documentDao.deleteDoc(document.getFileId());
                }
                folderDao.deleteFolder(fileId);
                return 0;
            } else if (fileId.charAt(0) == 'd') {
                documentDao.deleteDoc(fileId);
                return 0;
            } else {
                throw new OtherFail();
            }
        } catch (Exception e) {
            throw new OtherFail();
        }
    }

    @Override
    public void updateAuthRecur(String parentId, int newAuth) {
        List<Folder> folders = folderDao.selectSubDir(parentId);
        List<Document> documents = documentDao.selectSubDir(parentId);
        if (folders.size() == 0 && documents.size() == 0) {
            return;
        }
        for (Document document : documents) {
            if (newAuth < document.getNowAuth()) {
                documentDao.updateDoc(document.getFileId(), "now_auth", newAuth);
            }
        }
        for (Folder folder : folders) {
            if (newAuth < folder.getNowAuth()) {
                folderDao.updateFolder(folder.getFileId(), "now_auth", newAuth);
            }
            updateAuthRecur(folder.getFileId(), newAuth);
        }
    }
}
