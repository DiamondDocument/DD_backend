package diamondpick.dd_backend.Service.lyz;

import diamondpick.dd_backend.Dao.lyz.FolderDao;
import diamondpick.dd_backend.Entity.lyz.Folder;
import diamondpick.dd_backend.Service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    private FolderDao folderDao;

    @Override
    public void createFolder(Folder folder) {
        folderDao.createFolder(folder);
    }

    @Override
    public ArrayList<Folder> openFolder(String folderId) {
        return folderDao.openFolder(folderId);
    }


}
