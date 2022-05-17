package diamondpick.dd_backend.Dao;


import diamondpick.dd_backend.Entity.lyz.Message;
import diamondpick.dd_backend.Entity.yyh.TeamMessage;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Entity.zzy.Document;
import diamondpick.dd_backend.Entity.zzy.RecentDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserDao {

    /*
    周五之前实现
    周五之前实现
    周五之前实现
     */
    public User selectUser(@Param("userId") String userId);
    public TeamMessage selectTeam(@Param("teamId") String teamId);
    //RecentDocument的内容见Entity.zzy
    public ArrayList<RecentDocument> selectRecentDoc(@Param("userId") String userId);
    //注意，每个用户和文档的关系只有一条浏览记录，就是最近浏览的那条，也即输入的用户和文档如果browse表里面
    public void insertRecent(@Param("userId") String userId, @Param("docId") String docId);
    //////////////


    public void InsertNewUser(@Param("user") User user);

    public void insertUser(User user);
    public List<User> selectUserBy(@Param("name") String name, @Param("value")Object value);
    public void InsertRecentBrowse(@Param("message_id") int message_id);
    public List<Message> selectRecentBrowse(@Param("user_id") String user_id);

}
