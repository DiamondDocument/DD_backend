package diamondpick.dd_backend.Dao.zzy;

import diamondpick.dd_backend.Entity.zzy.Document;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CollectionDao {

    public ArrayList<Document> selectCollection(String collectorId);

    public void insertCollection(String docId, String collectorId);

}
