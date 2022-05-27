package diamondpick.dd_backend.yyh.Dao;


import diamondpick.dd_backend.Entity.lyz.Message;
import diamondpick.dd_backend.yyh.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {


    @Select("select * from users\n" +
            "    where user_id = #{userId}")
    public User selectUser(@Param("userId") String userId);
    @Select("select * from users\n" +
            "    where ${key} = #{value}")
    @ResultType(User.class)
    public List<User> selectUserBy(@Param("key") String key, @Param("value")Object value);
    @Insert("insert into users (user_id, nickname, password, gender, intro, email)\n" +
            "        values (#{userId}, #{nickname}, #{password}, #{gender}, #{intro}, #{email})")
    public void insertUser(User user);


    @Update("update users set ${key} = #{value} where user_id = #{userId}")
    public void updateUser(@Param("userId")String userId, @Param("key")String key, @Param("value")Object value);


    public void InsertRecentBrowse(@Param("message_id") int message_id);
    public List<Message> selectRecentBrowse(@Param("user_id") String user_id);

}
