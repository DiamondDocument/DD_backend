package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.yyh.TeamMessage;
import diamondpick.dd_backend.Entity.yyh.TeamMember;

public interface TeamService {
    public void registerNewTeam(TeamMessage teamMessage);
    public TeamMessage selectTeamByTeamId(String teamID);
    public TeamMember selectUserByUserIdInTeam(String userID,String teamID);
    public String[] selectTeamByUserId(String userId);
    public void registerNewMember(TeamMember teamMember);
}
