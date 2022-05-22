package diamondpick.dd_backend.Service.yyh;

import diamondpick.dd_backend.Dao.yyh.TeamDao;
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
    public TeamMessage selectTeamByTeamId(String teamID){
        return teamDao.selectTeam(teamID);
    }
    @Override
    public TeamMember selectUserByUserIdInTeam(String userID,String teamID){
        return teamDao.selectUserByUserIdInTeam(userID,teamID);
    }
    @Override
    public String[] selectTeamByUserId(String userId){
        return teamDao.selectTeamByUserId(userId);
    }
    @Override
    public void registerNewMember(TeamMember teamMember){
        teamDao.registerNewMember(teamMember);
    }
}
