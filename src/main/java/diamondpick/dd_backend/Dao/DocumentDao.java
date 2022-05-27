package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Document;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import javax.print.Doc;
import java.util.Date;
import java.util.List;


@Mapper
public interface DocumentDao {
    @Insert("insert into documents(doc_id,name,creator_id,self_auth,now_auth,parent_id,space_id)\n" +
            "    values(#{param1},#{param2},#{param3},#{param4},#{param4},#{param5}, #{param6})")
    public void insertDoc(String docId, String name, String creatorId, int authority, String parentId, String spaceId)throws DuplicateKeyException, DataIntegrityViolationException;

    @Insert("insert into document_collector values(#{param2}, #{param1})")
    public void insertCollection(String collectorId, String docId)throws DataIntegrityViolationException;


    /**如果这个报异常，可以捕获异常后插入*/
    @Insert("insert into user_recent(doc_id, user_id) values(#{param2}, #{param1})")
    public void insertRecent(String browserId, String docId)throws DataIntegrityViolationException;

    @Delete("delete from documents where doc_id = #{param1}")
    public void deleteDoc(String docId);

    @Delete("delete from document_collector where collector_id = #{param1} and doc_id = #{param2}")
    public void deleteCollection(String collectorId, String docId);

    @Update("update documents set ${key} = #{value} where doc_id = #{docId}")
    public void updateDoc(@Param("docId")String docId, @Param("key")String key, @Param("value")Object value)throws DataIntegrityViolationException, BadSqlGrammarException;

    @Update("update documents set is_delete = true , deleter_id = #{param2} , delete_time = now() where doc_id = #{param1}")
    public void updateToDelete(String docId, String deleterId)throws DataIntegrityViolationException;

    /** 更新浏览时间至现在时间 */
    @Update("update user_recent set browse_time = now() where user_id = #{param1} and doc_id = #{param2}")
    public void updateRecent(String browserId, String docId)throws DataIntegrityViolationException;

    /** @return 注意这里包括有创建者、修改者和删除者的昵称 */
    @Select("select doc.*, m.nickname as modifier_name, c.nickname as creator_name from documents as doc, users as m, users as c " +
            "where doc_id    = #{param1} and " +
            "doc.modifier_id = m.user_id and " +
            "doc.creator_id  = c.user_id ")
    public Document selectDoc(String docId);


    /** @return 只包括未删除的 */
    @Select("select doc.*, m.nickname as modifier_name, c.nickname as creator_name " +
            "from documents as doc, users as m, users as c, document_collector as co " +
            " where co.collector_id = #{param1} and " +
            " co.doc_id = doc.doc_id and " +
            "doc.modifier_id = m.user_id and " +
            "doc.creator_id = c.user_id and " +
            "doc.is_delete = false")
    public List<Document> selectCollection(String collectorId);


    @Select("select doc.*, m.nickname as modifier_name, c.nickname as creator_name " +
            "from documents as doc, users as m, users as c  " +
            " where doc.parent_id = #{param1} and " +
            "doc.modifier_id = m.user_id and " +
            "doc.creator_id = c.user_id and  " +
            "doc.is_delete = false           " )
    /** 只包括未删除的 */
    public List<Document> selectSubDir(String parentId);

    /**
     * @param type 可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     * @return 只包括未删除的
     */
    @Select("select doc.*, m.nickname as modifier_name, c.nickname as creator_name " +
            "from documents as doc, users as m, users as c, ${param1}s as s  " +
            "where " +
            "s.${param1}_id            = #{param1} and      " +
            "s.space_id           = doc.space_id and   " +
            "doc.modifier_id      = m.user_id and      " +
            "doc.creator_id       = c.user_id and      " +
            "doc.is_delete        = false              " )
    public List<Document> selectRootDir(String type, String spaceOwnerId);

    @Select("select doc.*, d.nickname as deleter_name, c.nickname as creator_name " +
            "from documents as doc, users as d, users as c, ${param1}s as s  " +
            "where " +
            "s.${param1}_id            = #{param2} and      " +
            "s.space_id           = doc.space_id and   " +
            "doc.deleter_id       = d.user_id and      " +
            "doc.creator_id       = c.user_id and      " +
            "doc.is_delete        = true               " )
    /**这里的额外信息没有修改者的名称，只有创建者和删除者的名称 */
    public List<Document> selectDeleted(String type, String spaceOwnerId);

    @Select("select doc.*, m.nickname as modifier_name, c.nickname as creator_name " +
            "from documents as doc, users as m, users as c, user_recent as r  " +
            "where " +
            "r.user_id            = #{param1} and      " +
            "s.space_id           = doc.space_id and   " +
            "doc.modifier_id      = m.user_id and      " +
            "doc.creator_id       = c.user_id and      " +
            "doc.doc_id           = r.doc_id           " +
            "doc.is_delete        = false              " +
            "order by r.browse_time desc               " +
            "limit  ${param2}                          " )
    /** 只包括未删除的，按照时间排序，如果多于limit个则显示limit个 */
    public List<Document> selectRecent(String browserId, int limit);


    @Select("select count(*) from documents")
    public int numOfDoc();

}
