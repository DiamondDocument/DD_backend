package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Comment;
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
    @Insert("insert into documents(doc_id,name,creator_id,parent_id,space_id)\n" +
            "    values(#{param1},#{param2},#{param3},#{param4}, #{param5})")
    public void insertDoc(String docId, String name, String creatorId, String parentId, String spaceId)throws DuplicateKeyException, DataIntegrityViolationException;

    @Insert("insert into document_collector values(#{param2}, #{param1})")
    public void insertCollection(String collectorId, String docId)throws DataIntegrityViolationException;


    /**如果这个报异常，可以捕获异常后插入*/
    @Insert("insert into user_recent(doc_id, user_id) values(#{param2}, #{param1})")
    public void insertRecent(String browserId, String docId)throws DataIntegrityViolationException;

    @Delete("delete from documents where doc_id = #{param1}")
    public void deleteDoc(String docId);

    /**
     * @param spaceId 删除指定space的所有文档（解散团队用）
     */
    @Delete("delete from documents where space_id = #{param1}")
    public void deleteDocInSpace(int spaceId);

    @Delete("delete from document_collector where collector_id = #{param1} and doc_id = #{param2}")
    public void deleteCollection(String collectorId, String docId);

    @Update("update documents set ${key} = #{value} where doc_id = #{docId}")
    public void updateDoc(@Param("docId")String docId, @Param("key")String key, @Param("value")Object value)throws DataIntegrityViolationException, BadSqlGrammarException;

    @Update("update documents set is_delete = true , deleter_id = #{param2} , delete_time = now() where doc_id = #{param1}")
    public void updateToDelete(String docId, String deleterId)throws DataIntegrityViolationException;

    /** 更新parentId代表的文件夹的子目录下的所有文件的now_auth（取自身的和输入参数中的最小值）*/
    @Update("update documents set now_auth = min(#{param2}, self_auth) where parent_id = #{param1}")
    public void updateSubDirAuth(String parentId, int newAuth)throws DataIntegrityViolationException;

    /** 更新某空间根目录下的所有文件夹的now_auth（取自身的和输入参数中的最小值）
     * @param type 可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     */
    @Update("update documents\n" +
            "set now_auth = min(#{param3}, self_auth)\n" +
            "where space_id in (select space_id from ${param1}s where ${param1}_id = #{param2}) and parent_id is null")
    public void updateRootDirAuth(String type, String spaceOwnerId, int newAuth)throws DataIntegrityViolationException;

    /** 更新浏览时间至现在时间 */
    @Update("update user_recent set browse_time = now() where user_id = #{param1} and doc_id = #{param2}")
    public void updateRecent(String browserId, String docId)throws DataIntegrityViolationException;

    /** @return 只要求返回documents表里的字段 */
    @Select("select\n" +
            "    doc2.*, m.nickname as modifier_name\n" +
            "    from  users m right join\n" +
            "        (select doc.*, c.nickname from  documents doc, users as c\n" +
            "            where doc.doc_id = #{param1} and\n" +
            "                  doc.creator_id = c.user_id) doc2\n" +
            "    on doc2.modifier_id = m.user_id\n")
    public Document selectDoc(String docId);


    /** @return 只输出未删除的，包括有创建者、修改者的昵称 */
    @Select("select\n" +
            "    doc2.*, m.nickname as modifier_name\n" +
            "    from  users m right join\n" +
            "        (select doc.*, c.nickname as creator_name from  documents doc, users as c, document_collector as co\n" +
            "            where doc.doc_id = co.doc_id and\n" +
            "                  doc.creator_id = c.user_id and\n" +
            "                  co.collector_id = #{param1} and\n" +
            "                  doc.is_delete = false) doc2\n" +
            "    on doc2.modifier_id = m.user_id")
    public List<Document> selectCollection(String collectorId);

    @Select("select DISTINCT doc_id from document_collector where doc_id = #{param2} and collector_id = #{param1}")
    public String selectCollectorAndDoc(String collectorId, String docId);



    /** @return 只输出未删除的，包括有创建者、修改者的昵称 */
    @Select("select\n" +
            "    doc2.*, m.nickname as modifier_name\n" +
            "    from  users m right join\n" +
            "        (select doc.*, c.nickname as creator_name from  documents doc, users  c\n" +
            "            where doc.creator_id = c.user_id and\n" +
            "                  doc.parent_id = #{param1} and\n" +
            "                  doc.is_delete = false) doc2\n" +
            "    on doc2.modifier_id = m.user_id" )
    public List<Document> selectSubDir(String parentId);

    /**
     * @param type 可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     * @return 只包括未删除的，包括有创建者、修改者的昵称
     */
    @Select("select\n" +
            "    doc2.*, m.nickname as modifier_name\n" +
            "    from  users m right join\n" +
            "        (select doc.*, c.nickname as creator_name from  documents doc, users c, ${param1}s s\n" +
            "            where doc.creator_id = c.user_id and\n" +
            "                  doc.space_id = s.space_id and\n" +
            "                  doc.parent_id is null and\n" +
            "                  doc.is_delete = false and\n" +
            "                  s.${param1}_id = #{param2}) doc2\n" +
            "    on doc2.modifier_id = m.user_id\n" )
    public List<Document> selectRootDir(String type, String spaceOwnerId);

    /**
     * @param type 可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     * @return 只包括已经删除的，包括有创建者、删除者的昵称
     */
    @Select("select\n" +
            "    doc2.*, d.nickname as deleter_name\n" +
            "    from  users d right join\n" +
            "        (select doc.*, c.nickname as creator_name from  documents doc, users c, ${param1}s s\n" +
            "            where doc.creator_id = c.user_id and\n" +
            "                  doc.space_id = s.space_id and\n" +
            "                  doc.parent_id is null and\n" +
            "                  doc.is_delete = true and\n" +
            "                  s.${param1}_id = #{param2}) doc2\n" +
            "    on doc2.deleter_id = d.user_id\n" )
    public List<Document> selectDeleted(String type, String spaceOwnerId);

    /** 只包括未删除的，包括有创建者、修改者的昵称，按照时间排序，如果多于limit个则显示limit个 */
    @Select("select\n" +
            "    doc2.*, m.nickname as modifier_name\n" +
            "    from  users m right join\n" +
            "        (select doc.*, c.nickname as creator_name, r.browse_time from  documents doc, users c, user_recent r\n" +
            "            where doc.creator_id = c.user_id and\n" +
            "                  doc.doc_id = r.doc_id and\n" +
            "                  doc.is_delete = false and\n" +
            "                  r.user_id = #{param1}) doc2\n" +
            "    on doc2.modifier_id = m.user_id\n" +
            "order by browse_time desc               " +
            "limit  ${param2}                          " )
    public List<Document> selectRecent(String browserId, int limit);


    /**
     * 查询文档最大id
     * @return 文档最大id
     */
    @Select("select DISTINCT doc_id\n" +
            "from documents\n" +
            "order by convert(doc_id using gbk) desc\n" +
            "limit 1")
    public String selectMaxId();


    @Insert("insert into comments(content, doc_id, creator_id) values(#{param1}, #{param3}, #{param2})")
    public void insertComment(String content, String userId, String docId);
    /** @return 要包括创建者名称 */
    @Select("select * , c.nickname as creator_name from comments, users c " +
            "where creator_id = c.user_id and " +
            "doc_id = #{param1}")
    public List<Comment> selectCommentByDoc(String docId);

    @Select("select * from comments " +
            "comment_id = #{param1} ")
    public Comment selectComment(int commentId);

    @Delete("delete from comments where comment_id = #{param1}")
    public void deleteComment(int commentId);

}
