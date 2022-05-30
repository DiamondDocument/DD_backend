package diamondpick.dd_backend.Dao;


import diamondpick.dd_backend.Entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

@Mapper
public interface UserDao {



    @Insert("insert into users (user_id, nickname, password, intro, email, space_id)\n" +
            "        values (#{param1}, #{param2}, #{param3}, #{param4}, #{param5}, #{param6})")
    public void insertUser(String userId, String nickname, String password, String intro, String email, int spaceId)throws DuplicateKeyException, DataIntegrityViolationException;

    @Update("update users set ${param2} = #{param3} where user_id = #{param1}")
    public void updateUser(String userId, String key, Object value)throws DataIntegrityViolationException, BadSqlGrammarException;

    @Select("select * from users\n" +
            "    where user_id = #{param1}")
    public User selectUser(String userId);
    @Select("select * from users\n" +
            "    where email = #{param1}")
    public User selectUserByEmail(String email);
    @Select("select distinct * from team_member\n" +
            "    where team_id = #{param1}")
    List<User> selectMember(String teamId);
    @Select("select * from users where user_id = (select captain_id from teams where team_id = #{param1} ")
    User selectCaption(String teamId);


//    @Select("select * from users\n" +
//            "    where ${param1} = #{param2}")
//    @ResultType(User.class)
//    public List<User> selectUserBy(String key, Object value);


//    /**
//     * @return 返回的User对象中只有用户名和用户昵称。
//     */
//    public User selectCreator(String docId);
//
//
//
//    /**
//     * @return 返回的User对象中只有用户名和用户昵称。
//     */
//    User selectModifier(String docId);
//
//    /**
//     * @return 返回的User对象中只有用户名和用户昵称。
//     */
//    User selectSender(String msgId);
//
//    /**
//     * @return 返回的User对象中只有用户名和用户昵称。
//     */
//    User selectReceiver(String msgId);


}
