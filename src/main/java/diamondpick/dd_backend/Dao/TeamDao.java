package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.TeamMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamDao {

    //
    public void selectTeam(String teamId);

    void registerNewTeam(TeamMessage teamMessage);
    TeamMessage selectTeam(int teamID);
    TeamMember selectUserByUserIdInTeam(String userID,int teamID);
    void registerNewMember(TeamMember teamMember);
    String selectCaptainID(int teamID);
    int selectTeamID(String captainID);
}
