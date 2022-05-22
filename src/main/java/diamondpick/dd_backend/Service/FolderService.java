package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.Folder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface FolderService {
    String newFolder(String folderName, String creatorId, String parentId, String spaceId, int auth);

    ArrayList<Folder> openFolder(String folderId);
}
