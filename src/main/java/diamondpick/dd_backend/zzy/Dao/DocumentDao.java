package diamondpick.dd_backend.zzy.Dao;

import diamondpick.dd_backend.zzy.Entity.Document;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


@Mapper
public interface DocumentDao {

    @Select("select * from documents where doc_id = #{param1}")
    public Document selectDoc(String docId);

    @Select("select * from documents where ${key} = #{value}")
    @ResultType(Document.class)
    public ArrayList<Document> selectDocs(@Param("key")String key, @Param("value")Object value);

    @Delete("delete from documents where doc_id = #{param1}")
    public void deleteDoc(String docId);

    @Update("update documents set ${key} = #{value} where doc_id = #{docId}")
    public void updateDoc(@Param("docId")String docId, @Param("key")String key, @Param("value")Object value);



    @Insert("insert into documents(doc_id,name,creator_id,self_auth,now_auth,parent_id,space_id)\n" +
            "    values(#{param1},#{param2},#{param3},#{param4},#{param4},#{param5}, #{param6})")
    public void insertDoc(String docId, String name, String creatorId, int authority, String parentId, String spaceId);

    @Select("select count(*) from documents")
    public Integer numOfDoc();


}
