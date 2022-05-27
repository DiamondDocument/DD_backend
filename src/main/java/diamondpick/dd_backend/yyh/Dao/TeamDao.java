package diamondpick.dd_backend.yyh.Dao;

import diamondpick.dd_backend.yyh.Entity.Team;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

public interface TeamDao {

    public void insertTeam(String teamId, String name, String intro, String captainId)throws DataIntegrityViolationException, DuplicateKeyException;
    public void insertMember(String teamId, String memberId)throws DataIntegrityViolationException;

    public void deleteTeam(String teamId);
    public void deleteMember(String teamId, String memberId);


    public void updateTeam(String teamId, String key, String newValue)throws DataIntegrityViolationException, BadSqlGrammarException;


    Team selectTeam(String teamId);

    /**
     * @return 返回的Team对象中只要求有团队id和团队名称。
     */
    List<Team> selectTeamByMember(String memberId);

    /**
     * @return 返回的Team对象中只要求有团队id和团队名称。
     */
    Team selectTeamByCaptain(String captainId);

}
