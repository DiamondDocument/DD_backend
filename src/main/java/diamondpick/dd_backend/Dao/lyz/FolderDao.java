package diamondpick.dd_backend.Dao.lyz;

import diamondpick.dd_backend.Entity.lyz.Folder;

import java.util.ArrayList;

public interface FolderDao {
    void newFolder(Folder folder);

    ArrayList<Folder> openFolder(String folderId);

    String getParentId(String fileId);

    ArrayList<Folder> getFolderBySpaceId(String spaceId);

    ArrayList<Folder> getFolderByParentId(String parentId);
}
