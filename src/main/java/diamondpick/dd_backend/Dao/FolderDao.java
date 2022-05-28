package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Folder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FolderDao {

    public void insertFolder(String folderId, String name, String creatorId, String parentId, String spaceId)throws DuplicateKeyException, DataIntegrityViolationException;

    public void deleteFolder(String docId);

    public void updateFolder(String folderId, String key, Object newValue)throws DataIntegrityViolationException, BadSqlGrammarException;

    /**将文件夹设置为删除状态，注意更新delete_time*/
    public void updateToDelete(String folderId, String deleterId)throws DataIntegrityViolationException;

    /** 更新parentId代表的文件夹的子目录下的所有文件夹的now_auth（取自身的和输入参数中的最小值）*/
    public void updateSubDirAuth(String parentId, int newAuth)throws DataIntegrityViolationException;

    /** 更新某空间根目录下的所有文件夹的now_auth（取自身的和输入参数中的最小值）
     * @param type 可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     */
    public void updateRootDirAuth(String type, String spaceOwnerId, int newAuth)throws DataIntegrityViolationException;

    /**
     * @return 注意这里包括有创建者名称
     */
    public Folder selectFolder(String folderId);
    /**
     * @return 注意这里包括有创建者名称，并且只有未删除的文件夹
     */
    public List<Folder> selectSubDir(String parentId);
    /**
     * @param type 可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     * @return 只包括未删除的，返回的对象包括有创建者名称
     */
    public List<Folder> selectRootDir(String type, String spaceOwnerId)throws BadSqlGrammarException;

    /**
     * @param type 可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     * @return 已删除的文件夹，返回的对象包括有创建者名称
     */
    public List<Folder> selectDeleted(String type, String spaceOwnerId)throws BadSqlGrammarException;


}
