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

    @Insert("insert into document_collector values(#{param2}, #{param1}")
    public void insertCollection(String collectorId, String docId)throws DataIntegrityViolationException;


    /**如果这个报异常，可以捕获异常后插入*/
    @Insert("insert into user_recent values(#{param2}, #{param1})")
    public void insertRecent(String browserId, String docId)throws DataIntegrityViolationException;

    @Delete("delete from documents where doc_id = #{param1}")
    public void deleteDoc(String docId);

    @Delete("delete from document_collector where collector_id = #{param1} and doc_id = #{param2}")
    public void deleteCollection(String collectorId, String docId);

    @Update("update documents set ${key} = #{value} where doc_id = #{docId}")
    public void updateDoc(@Param("docId")String docId, @Param("key")String key, @Param("value")Object value)throws DataIntegrityViolationException, BadSqlGrammarException;

    /** 更新浏览时间至现在时间 */
    @Update("update user_recent set browse_time = now() where browser_id = #{param1} and doc_id = #{param2}")
    public void updateRecent(String browserId, String docId)throws DataIntegrityViolationException;

    /**@return 注意这里包括有创建者、修改者和删除者的名称*/
    @Select("select * from documents as doc, users as m, users as c " +
            " where doc_id = #{param1} and " +
            "doc.modifier_id = m.user_id and " +
            "doc.creator_id = c.user_id ")
    public Document selectDoc(String docId);

    @Select("select * from documents")
    public List<Document> selectCollection(String collectorId);
    public List<Document> selectSubdirectory(String parentId);
    public List<Document> selectRootDirectoryUSpace(String userId);
    public List<Document> selectRootDirectoryTSpace(String teamId);


    public List<Document> selectRecent(String browserId, int limit);

    @Select("select count(*) from documents")
    public Integer numOfDoc();


    /*
    @Select("select * from documents where ${key} = #{value}")
    @ResultType(Document.class)
    public List<Document> selectDocBy(@Param("key")String key, @Param("value")Object value);
     */

}
