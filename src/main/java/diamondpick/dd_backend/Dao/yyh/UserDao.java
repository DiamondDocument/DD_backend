package diamondpick.dd_backend.Dao.yyh;


import diamondpick.dd_backend.Entity.lyz.Message;
import diamondpick.dd_backend.Entity.yyh.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao {


    public User selectUser(@Param("userId") String userId);

    //////////////


    @Update("update users set ${key} = #{value} where user_id = #{userId}")
    public void updateUser(@Param("userId")String userId, @Param("key")String key, @Param("value")Object value);


    public void InsertNewUser(@Param("user") User user);

    public void insertUser(User user);
    public List<User> selectUserBy(@Param("name") String name, @Param("value")Object value);
    public void InsertRecentBrowse(@Param("message_id") int message_id);
    public List<Message> selectRecentBrowse(@Param("user_id") String user_id);

}
