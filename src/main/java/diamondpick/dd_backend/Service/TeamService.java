package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.yyh.Team;
import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Exception.NotExist.UserNotExist;

import java.util.ArrayList;

public interface TeamService {
    public void registerNewTeam(Team team);
    public Team selectTeamByTeamId(String teamID);
    public TeamMember selectUserByUserIdInTeam(String userID,String teamID);

    public void registerNewMember(TeamMember teamMember);
}
