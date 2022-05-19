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


    public User selectUser(@Param("userId") String userId);

    //////////////





    public void InsertNewUser(@Param("user") User user);

    public void insertUser(User user);
    public List<User> selectUserBy(@Param("name") String name, @Param("value")Object value);
    public void InsertRecentBrowse(@Param("message_id") int message_id);
    public List<Message> selectRecentBrowse(@Param("user_id") String user_id);

}
