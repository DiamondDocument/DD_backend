package diamondpick.dd_backend.Dao;


import diamondpick.dd_backend.Entity.Message;
import diamondpick.dd_backend.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    public void InsertNewUser(@Param("user") User user);
    public User selectUserById(@Param("user_id") String user_id);
    public boolean insertUser(@Param("user") User user);
    public List<User> selectUserBy(@Param("name") String name, @Param("value")Object value);
    public void InsertRecentBrowse(@Param("message_id") int message_id);
    public List<Message> selectRecentBrowse(@Param("user_id") String user_id);
}
