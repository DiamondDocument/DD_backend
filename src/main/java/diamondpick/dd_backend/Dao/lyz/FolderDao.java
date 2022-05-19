package diamondpick.dd_backend.Dao.lyz;

import diamondpick.dd_backend.Entity.lyz.Folder;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface FolderDao {
    void createFolder(Folder folder);

    ArrayList<Folder> openFolder(String folderId);

    String getParentId(String fileId);
}
