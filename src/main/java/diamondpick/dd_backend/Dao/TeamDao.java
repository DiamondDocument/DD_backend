package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.TeamMember;
import diamondpick.dd_backend.Entity.TeamMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamDao {
    public void registerNewTeam(TeamMessage teamMessage);
    public TeamMessage selectTeamByTeamId(int teamID);
    public TeamMember selectUserByUserIdInTeam(int userID,int teamID);
    public void registerNewMember(TeamMember teamMember);
}
