package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Folder;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

@Mapper
public interface FolderDao {
    /**
     * 插入文件夹
     *
     * @param folderId  文件夹id
     * @param name      文件夹名称
     * @param creatorId 创建者id
     * @param parentId  父文件夹id
     * @param spaceId   所属空间id
     * @throws DuplicateKeyException           主键冲突异常
     * @throws DataIntegrityViolationException 数据完整性异常
     */
    @Insert("insert into folders(folder_id, name, creator_id, parent_id, space_id)\n" +
            "values (#{param1}, #{param2}, #{param3}, #{param4}, #{param5})")
    public void insertFolder(String folderId, String name, String creatorId, String parentId, String spaceId) throws DuplicateKeyException, DataIntegrityViolationException;

    /**
     * 删除文档的文件夹
     *
     * @param docId 文档id
     */
    @Update("delete from folders where folder_id = #{param1}")
    public void deleteFolder(String docId);

    /**
     * @param spaceId 删除指定space的所有文件夹（解散团队用）
     */
    @Delete("delete from folders where space_id = #{param1}")
    public void deleteDocInSpace(int spaceId);

    /**
     * 更新文件夹
     *
     * @param folderId 文件夹id
     * @param key      更新的字段
     * @param newValue 新值
     * @throws DataIntegrityViolationException 数据完整性异常
     * @throws BadSqlGrammarException          sql语法错误
     */
    @Update("update folders\n" +
            "set ${param2} = #{param3}\n" +
            "where folder_id = #{param1}")
    public void updateFolder(String folderId, String key, Object newValue) throws DataIntegrityViolationException, BadSqlGrammarException;

    /**
     * 将文件夹设置为删除状态，注意更新delete_time
     *
     * @param folderId  文件夹id
     * @param deleterId 删除者id
     * @throws DataIntegrityViolationException 数据完整性异常
     */
    @Update("update folders\n" +
            "set is_delete = true, deleter_id = #{param2}, delete_time = now()\n" +
            "where folder_id = #{param1}")
    public void updateToDelete(String folderId, String deleterId) throws DataIntegrityViolationException;

    /**
     * 更新parentId代表的文件夹的子目录下的所有文件夹的now_auth
     * 取自身的和输入参数中的最小值
     *
     * @param parentId 父文件夹id
     * @param newAuth  新的now_auth
     * @throws DataIntegrityViolationException 数据完整性异常
     */
    @Update("update folders\n" +
            "set now_auth = min(#{param2}, self_auth)\n" +
            "where parent_id = #{param1}")
    public void updateSubDirAuth(String parentId, int newAuth) throws DataIntegrityViolationException;

    /**
     * 更新某空间根目录下的所有文件夹的now_auth
     * 取自身的和输入参数中的最小值
     *
     * @param type         可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     */
    @Update("update folders\n" +
            "set now_auth = min(#{param3}, self_auth)\n" +
            "where space_id in (select space_id from ${param1}s where ${param1}_id = #{param2}) and parent_id is null")
    public void updateRootDirAuth(String type, String spaceOwnerId, int newAuth) throws DataIntegrityViolationException;

    /**
     * 查询文件夹
     *
     * @param folderId 文件夹id
     * @return 文件夹(包括创建者名称)
     */
    @Select("select f.*, u.nickname as creator_name\n" +
            "from folders f,\n" +
            "     users u\n" +
            "where f.creator_id = u.user_id\n" +
            "  and f.folder_id = #{param1}")
    public Folder selectFolder(String folderId);

    /**
     * 查询子文件夹
     *
     * @param parentId 父文件夹id
     * @return 文件夹列表（包括创建者名称，并且只有未删除的文件夹）
     */
    @Select("select f.*, u.nickname as creator_name\n" +
            "from folders f,\n" +
            "     users u\n" +
            "where f.parent_id = #{param1} and\n" +
            "      f.is_delete = false and\n" +
            "      f.creator_id = u.user_id")
    public List<Folder> selectSubDir(String parentId);

    /**
     * 查询空间根目录下的所有文件夹
     *
     * @param type         可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     * @return 文件夹列表（包括创建者名称，并且只有未删除的文件夹）
     */
    @Select("select f.*, u.nickname as creator_name\n" +
            "from folders f,\n" +
            "     users u,\n" +
            "     ${param1}s s\n" +
            "where s.${param1}_id = #{param2}\n" +
            "  and s.space_id = f.space_id\n" +
            "  and f.creator_id = u.user_id\n" +
            "  and f.is_delete = false\n" +
            "  and f.parent_id is null\n")
    public List<Folder> selectRootDir(String type, String spaceOwnerId) throws BadSqlGrammarException;

    /**
     * 查询空间中已删除的文件夹
     *
     * @param type         可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     * @return 文件夹列表（包括创建者名称，并且只有已删除的文件夹）
     */
    @Select("select f.*, u.nickname as creator_name\n" +
            "from folders f,\n" +
            "     users u,\n" +
            "     ${param1}s s\n" +
            "where s.${param1}_id = #{param2}\n" +
            "  and s.space_id = f.space_id\n" +
            "  and f.creator_id = u.user_id\n" +
            "  and f.is_delete = true")
    public List<Folder> selectDeleted(String type, String spaceOwnerId) throws BadSqlGrammarException;

    /**
     * 查询文件夹最大id
     * @return 文件夹最大id
     */
    @Select("select folder_id\n" +
            "from folders\n" +
            "order by convert(folder_id using gbk) desc\n" +
            "limit 1")
    public String selectMaxId();
}
