package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Team;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

@Mapper
public interface TeamDao {

    @Insert("insert into teams(team_id,name,intro,captain_id,space_id)\n" +
            "    values(#{param1},#{param2},#{param3},#{param4}, #{param5})")
    public void insertTeam(String teamId, String name, String intro, String captainId, int spaceId)throws DataIntegrityViolationException, DuplicateKeyException;
    @Insert("insert into team_member(team_id,member_id)\n" +
            "    values(#{param1},#{param2})")
    public void insertMember(String teamId, String memberId)throws DataIntegrityViolationException;
    @Delete("delete from teams where team_id = #{param1}")
    public void deleteTeam(String teamId);
    @Delete("delete from team_member where team_id = #{param1} and member_id = #{param2}")
    public void deleteMember(String teamId, String memberId);


    /**
     * @param key 表中对应的字段名
     * @param newValue key要更新为的值
     * @throws BadSqlGrammarException 仅当key输入错误会抛出
     */
    @Update("update teams set ${key} = #{newValue} where team_id = #{teamId}")
    public void updateTeam(@Param("teamId")String teamId, @Param("key")String key, @Param("newValue")String newValue)throws DataIntegrityViolationException, BadSqlGrammarException;

    @Select("select * from teams where team_id = #{param1}" )
    Team selectTeam(String teamId);

    @Select("select * from teams where team_id in (select team_id from team_member where member_id = #{param1})")
    List<Team> selectTeamByMember(String memberId);

    @Select("select * from teams where captain_id = #{param1}")
    List<Team> selectTeamByCaptain(String captainId);

}
