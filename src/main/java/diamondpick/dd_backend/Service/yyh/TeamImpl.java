package diamondpick.dd_backend.Service.yyh;

import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.TeamMessage;
import diamondpick.dd_backend.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamImpl implements TeamService {
    @Autowired
    private TeamDao teamDao;
    @Override
    public void registerNewTeam(TeamMessage teamMessage){
        teamDao.registerNewTeam(teamMessage);
    }
    @Override
    public TeamMessage selectTeamByTeamId(int teamID){
        return teamDao.selectTeam(teamID);
    }
    @Override
    public TeamMember selectUserByUserIdInTeam(String userID,int teamID){
        return teamDao.selectUserByUserIdInTeam(userID,teamID);
    }
    @Override
    public void registerNewMember(TeamMember teamMember){
        teamDao.registerNewMember(teamMember);
    }
}