package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.Folder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface FolderService {
    void createFolder(Folder folder);

    ArrayList<Folder> openFolder(String folderId);
}
