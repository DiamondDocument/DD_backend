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
    public void deleteFile(String fileId, String userId) throws OtherFail {
        try {
            if (authService.checkFileAuth(fileId, userId) < 4) {
                throw new NoAuth();
            }
            if (fileId.charAt(0) == 'f') {
                folderDao.updateToDelete(fileId, userId);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateToDelete(fileId, userId);
            } else {
                throw new OtherFail();
            }
        } catch (Exception e) {
            throw new OtherFail();
        }
    }

    @Override
    public void DeletePermanently(String fileId) throws OtherFail, NoAuth {
        try {
            if (fileId.charAt(0) == 'f') {
                folderDao.deleteFolder(fileId);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.deleteDoc(fileId);
            } else {
                throw new OtherFail();
            }
        } catch (Exception e) {
            throw new OtherFail();
        }
    }
}
