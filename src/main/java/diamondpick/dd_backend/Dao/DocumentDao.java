package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.zzy.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;


@Mapper
public interface DocumentDao {

    public Document selectDoc(String docId);
    public void deleteDoc(String docId);
    /**
     *
     * @param docId
     * @param key 对应着Documents表中的相应字段
     * @param value 对应着要更新的值
     */
    @Update("update documents set ${key} = #{value} where doc_id = #{docId}")
    public void updateDoc(@Param("docId")String docId, @Param("key")String key, @Param("value")Object value);






    //////////////////////
    ////个人
    //////////////////////

    public void insertDoc(String docId, String name, String creatorId, int authority, String parentId);

    public Integer numOfDoc();



}
