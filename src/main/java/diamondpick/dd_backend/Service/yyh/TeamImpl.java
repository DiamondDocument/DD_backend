package diamondpick.dd_backend.Service.yyh;

import diamondpick.dd_backend.Dao.yyh.TeamDao;
import diamondpick.dd_backend.Dao.yyh.UserDao;
import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.Team;
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
    public void registerNewTeam(Team team){
        teamDao.registerNewTeam(team);
    }
    @Override
    public Team selectTeamByTeamId(String teamID){
        return teamDao.selectTeam(teamID);
    }
    @Override
    public TeamMember selectUserByUserIdInTeam(String userID,String teamID){
        return teamDao.selectUserByUserIdInTeam(userID,teamID);
    }
    @Override
    public ArrayList<Team> selectTeamByUserId(String userId){
        return teamDao.selectTeamByUserId(userId);
    }
    @Override
    public void registerNewMember(TeamMember teamMember){
        teamDao.registerNewMember(teamMember);
    }
}
