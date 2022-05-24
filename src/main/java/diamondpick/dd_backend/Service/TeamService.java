package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.yyh.Team;
import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.Illegal.TeamNameIllegal;
import diamondpick.dd_backend.Exception.NotExist.TeamNotExist;
import diamondpick.dd_backend.Exception.OperationFail;

import java.util.ArrayList;

public interface TeamService {
    public void addTeam(String name, String intro, String captainId)throws OperationFail, TeamNameIllegal;
    public Team selectTeamByTeamId(String teamId) throws TeamNotExist;
    public ArrayList<User> selectUsersInTeam(String teamId)throws TeamNotExist;
    public void modifyName(String teamId)throws TeamNotExist, OperationFail;
    public void modifyIntro(String teamId)throws TeamNotExist, OperationFail;
    


    public TeamMember selectUserByUserIdInTeam(String userID,String teamID);

    public void registerNewMember(TeamMember teamMember);
}
