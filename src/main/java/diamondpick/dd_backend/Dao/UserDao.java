package diamondpick.dd_backend.Dao;


import diamondpick.dd_backend.Entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

@Mapper
public interface UserDao {
    @Insert("insert into users (user_id, nickname, password, intro, email, space_id) values (#{param1}, #{param2}, #{param3}, #{param4}, #{param5}, #{param6})")
    public void insertUser(String userId, String nickname, String password, String intro, String email, int spaceId)throws DuplicateKeyException, DataIntegrityViolationException;

    @Update("update users set ${param2} = #{param3} where user_id = #{param1}")
    public void updateUser(String userId, String key, Object value)throws DataIntegrityViolationException, BadSqlGrammarException;

    @Select("select * from users\n" +
            "    where user_id = #{param1}")
    public User selectUser(String userId);
    @Select("select * from users\n" +
            "    where email = #{param1}")
    public User selectUserByEmail(String email);
    @Select("select * from users where user_id in (select distinct member_id from team_member\n" +
            "    where team_id = #{param1})")
    List<User> selectMember(String teamId);
    @Select("select user_id, nickname, password, users.intro, email, users.space_id from users,teams where user_id =captain_id and team_id = #{param1} ")
    User selectCaption(String teamId);




}
