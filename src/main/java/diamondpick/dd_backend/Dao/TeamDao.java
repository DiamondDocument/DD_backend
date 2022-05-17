package diamondpick.dd_backend.Dao;

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
    public TeamMessage selectTeam(@Param("teamId") String teamId);
    //////////////




    void registerNewTeam(TeamMessage teamMessage);
    TeamMessage selectTeam(int teamID);
    TeamMember selectUserByUserIdInTeam(String userID,int teamID);
    void registerNewMember(TeamMember teamMember);
    String selectCaptainID(int teamID);
    int selectTeamID(String captainID);
}
