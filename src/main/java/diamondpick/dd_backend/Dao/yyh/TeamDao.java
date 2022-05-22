package diamondpick.dd_backend.Dao.yyh;

import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.TeamMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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




    void registerNewTeam(TeamMessage teamMessage);
    TeamMessage selectTeam(String teamID);
    TeamMember selectUserByUserIdInTeam(String userID,String teamID);
    void registerNewMember(TeamMember teamMember);
    String selectCaptainID(String teamID);
    int selectTeamID(String captainID);
}
