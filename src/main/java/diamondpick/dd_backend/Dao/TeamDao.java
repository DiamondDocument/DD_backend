package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

@Mapper
public interface TeamDao {

    public void insertTeam(String teamId, String name, String intro, String captainId, int spaceId)throws DataIntegrityViolationException, DuplicateKeyException;
    public void insertMember(String teamId, String memberId)throws DataIntegrityViolationException;

    public void deleteTeam(String teamId);
    public void deleteMember(String teamId, String memberId);


    /**
     * @param key 表中对应的字段名
     * @param newValue key要更新为的值
     * @throws BadSqlGrammarException 仅当key输入错误会抛出
     */
    public void updateTeam(String teamId, String key, String newValue)throws DataIntegrityViolationException, BadSqlGrammarException;


    Team selectTeam(String teamId);


    List<Team> selectTeamByMember(String memberId);


    List<Team> selectTeamByCaptain(String captainId);

}
