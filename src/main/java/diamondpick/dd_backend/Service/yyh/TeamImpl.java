package diamondpick.dd_backend.Service.yyh;

import diamondpick.dd_backend.Dao.yyh.TeamDao;
import diamondpick.dd_backend.Dao.yyh.UserDao;
import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.Team;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Exception.Illegal.TeamNameIllegal;
import diamondpick.dd_backend.Exception.NotExist.TeamNotExist;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeamImpl implements TeamService {
    @Autowired
    private TeamDao teamDao;
    private UserDao userDao;

    @Override
    public void addTeam(String name, String intro, String captainId) throws OperationFail, TeamNameIllegal {

    }

    @Override
    public Team selectTeamByTeamId(String teamID){
        return teamDao.selectTeam(teamID);
    }

    @Override
    public ArrayList<User> selectUsersInTeam(String teamId) throws TeamNotExist {
        return null;
    }

    @Override
    public void modifyName(String teamId) throws TeamNotExist, OperationFail {

    }

    @Override
    public void modifyIntro(String teamId) throws TeamNotExist, OperationFail {

    }

    @Override
    public TeamMember selectUserByUserIdInTeam(String userID,String teamID){
        return teamDao.selectUserByUserIdInTeam(userID,teamID);
    }

    @Override
    public void registerNewMember(TeamMember teamMember){
        teamDao.registerNewMember(teamMember);
    }
}
