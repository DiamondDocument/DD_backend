package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.NotExist.FolderNotExist;
import diamondpick.dd_backend.Exception.NotExist.SpaceNotExist;
import diamondpick.dd_backend.Exception.NotExist.UserNotExist;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Entity.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface FolderService {

    //返回id
    public String newFolder(String folderName, String creatorId, String parentId, String spaceId, int auth)throws OperationFail;



    public List<Document> getRootFolderInSpace(String spaceId)throws SpaceNotExist;
    public List<Folder> getFolderInFolder(String folderId)throws FolderNotExist;
    public void changeAuth(String folderId)throws FolderNotExist, NoAuth;
    int checkAuth(String folderId, String userId)throws FolderNotExist, UserNotExist;


    ArrayList<Folder> openFolder(String folderId);

    ArrayList<Folder> getFolderBySpaceId(String spaceId);

    ArrayList<Folder> getFolderByParentId(String parentId);
}
