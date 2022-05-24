package diamondpick.dd_backend.zzy.Dao;

import diamondpick.dd_backend.zzy.Entity.Document;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CollectionDao {

    @Insert("insert into document_collector values(#{param1}, #{param2}")
    public void insertCollection(String docId, String collectorId);
    @Delete("delete from document_collector where doc_id = #{param1} and collector_id = #{param2}")
    public void deleteCollection(String docId, String collectorId);
    @Select("select doc_id from document_collector where doc_id = #{param1} and collector_id = #{param2}")
    public String selectCollection(String docId, String collectorId);


    @Select("select documents.name as name,\n" +
            "    documents.doc_id as id,\n" +
            "    documents.creator_id as creatorid,\n" +
            "    documents.create_time as createtime,\n" +
            "    documents.modifier_id as modifierid,\n" +
            "    documents.self_auth as selfauth,\n" +
            "    documents.now_auth as nowauth,\n" +
            "    documents.is_editing as isediting\n" +
            "    from documents join document_collector\n" +
            "    on documents.doc_id = document_collector.doc_id\n" +
            "    and collector_id = #{param1}")
    @ResultType(Document.class)
    public ArrayList<Document> selectCollections(String userId);

}
