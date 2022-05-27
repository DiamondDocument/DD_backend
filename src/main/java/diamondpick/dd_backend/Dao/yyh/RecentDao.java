package diamondpick.dd_backend.Dao.yyh;

import diamondpick.dd_backend.Old.zzy.Entity.RecentDocument;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface RecentDao {

    /*
    周五之前实现
    周五之前实现
    周五之前实现
     */
    //////////////
    //RecentDocument的内容见Entity.zzy
    public ArrayList<RecentDocument> selectRecentDoc(@Param("userId") String userId);
    //注意，每个用户和文档的关系只有一条浏览记录，就是最近浏览的那条，也即输入的用户和文档如果browse表里面
    public void insertRecent(@Param("userId") String userId, @Param("docId") String docId);

}
