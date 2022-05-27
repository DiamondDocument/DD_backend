package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.lyz.Folder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FolderDao {

    public void insertFolder(String folderId, String name, String creatorId, int authority, String parentId, String spaceId)throws DuplicateKeyException, DataIntegrityViolationException;

    public void deleteFolder(String docId);

    public void updateFolder(String folderId, String key, Object newValue)throws DataIntegrityViolationException, BadSqlGrammarException;


    /**
     * @return 注意这里包括有创建者名称
     */
    public Folder selectFolder(String folderId);
    /**
     * @return 注意这里包括有创建者名称
     */
    public List<Folder> selectSubDir(String parentId);
    /**
     * @return 注意这里包括有创建者名称
     */
    public List<Folder> selectRootDirInUSpace(String userId);
    /**
     * @return 注意这里包括有创建者名称
     */
    public List<Folder> selectRootDirInTSpace(String teamId);

}
