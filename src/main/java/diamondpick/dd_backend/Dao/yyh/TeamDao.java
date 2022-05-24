package diamondpick.dd_backend.Dao.yyh;

import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.Team;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface TeamDao {

     /*
    周五之前实现
    周五之前实现
    周五之前实现
     */
    //////////////
    //public TeamMessage selectTeam(@Param("teamId") String teamId);
    //////////////


    Team selectTeam(String teamID);
    void registerNewTeam(Team team);
    TeamMember selectUserByUserIdInTeam(String userID,String teamID);
    void registerNewMember(TeamMember teamMember);
    String selectCaptainID(String teamID);
    String selectTeamID(String captainID);
    ArrayList<Team> selectTeamByUserId(String userId);
}
