package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.TeamMessage;
import diamondpick.dd_backend.Entity.TeamMember;

public interface TeamService {
    public void registerNewTeam(TeamMessage teamMessage);
    public TeamMessage selectTeamByTeamId(int teamID);
    public TeamMember selectUserByUserIdInTeam(int userID,int teamID);
    public void registerNewMember(TeamMember teamMember);
}
