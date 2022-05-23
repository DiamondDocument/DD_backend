package diamondpick.dd_backend.Dao.zzy;

import diamondpick.dd_backend.Entity.zzy.Document;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


@Mapper
public interface DocumentDao {

    public Document selectDoc(String docId);

    @Delete("delete from documents where doc_id = #{param1}")
    public void deleteDoc(String docId);

    @Update("update documents set ${key} = #{value} where doc_id = #{docId}")
    public void updateDoc(@Param("docId")String docId, @Param("key")String key, @Param("value")Object value);

    @Insert("insert into document_collector values(#{param1}, #{param2}")
    public void insertCollection(String docId, String collectorId);
    @Delete("delete from document_collector where doc_id = #{param1} and collector_id = #{param2}")
    public void deleteCollection(String docId, String collectorId);
    @Select("select doc_id from document_collector where doc_id = #{param1} and collector_id = #{param2}")
    public String selectCollection(String docId, String collectorId);


    public ArrayList<Document> selectCollections(String userId);

    public void insertDoc(String docId, String name, String creatorId, int authority, String parentId, String spaceId);

    @Select("select count(*) from documents")
    public Integer numOfDoc();


}
