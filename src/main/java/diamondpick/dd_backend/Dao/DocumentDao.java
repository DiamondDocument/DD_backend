package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.zzy.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    public void updateDoc(String docId, String key, String value);



    //////////////////////
    ////个人
    //////////////////////
    /**
     *
     * @param documentId
     * @param fatherId 若为null则可以设置为null
     */
    public void updateFather(String documentId, String fatherId);
    public void updateSelfAuth(String documentId, int selfAuthority);


    public void updateNowAuth(String documentId, int nowAuthority);
    public void updateName(String documentId, String name);


    public Integer numOfDoc();
    public void insertDoc(@Param("name") String name, @Param("creatorId") String creatorId, @Param("authority") int authority, @Param("fatherId") String fatherId);

    public void updateNowAuth(String did, Integer auth);
    public void updateSelfAuth(String did, Integer auth);

    public ArrayList<Document> selectByCollector(String cId);
    public void insertDocCollector(@Param("dId")String dId, @Param("uId")String uId);


}
