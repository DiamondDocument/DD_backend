package diamondpick.dd_backend.Service.lyz;

import diamondpick.dd_backend.Dao.lyz.FolderDao;
import diamondpick.dd_backend.Entity.lyz.Folder;
import diamondpick.dd_backend.Service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    private FolderDao folderDao;

    @Override
    public String newFolder(String folderName, String creatorId, String parentId, String spaceId, int auth) {
        Folder folder;
        try {
            folder = new Folder(folderName, creatorId, new Date(System.currentTimeMillis()), parentId, spaceId, auth, auth);
            folderDao.newFolder(folder);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return folder.getFolderId();
    }

    @Override
    public ArrayList<Folder> openFolder(String folderId) {
        return folderDao.openFolder(folderId);
    }


}
