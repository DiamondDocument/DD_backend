package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.TeamMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamDao {
    void registerNewTeam(TeamMessage teamMessage);
    TeamMessage selectTeamByTeamId(int teamID);
    TeamMember selectUserByUserIdInTeam(String userID,int teamID);
    void registerNewMember(TeamMember teamMember);
    String selectCaptainID(int teamID);
    int selectTeamID(String captainID);
}
