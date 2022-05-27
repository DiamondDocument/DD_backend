package diamondpick.dd_backend.yyh.ServiceImp;

import diamondpick.dd_backend.yyh.Dao.TeamDao;
import diamondpick.dd_backend.yyh.Dao.UserDao;
import diamondpick.dd_backend.yyh.Entity.Team;
import diamondpick.dd_backend.yyh.Entity.User;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.Team.*;
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
    public void dismissTeam(String teamId, String captainId) throws OperationFail {

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
    public void removeMember(String teamId, String captainId, String memberId) throws NotMember, OperationFail {

    }

    @Override
    public void transferRank(String teamId, String oldCaptainId, String newCaptainId) throws NotMember, AlreadyCaption, OperationFail {

    }

    @Override
    public void InviteMember(String teamId, String userId) throws NotYetDeal, AlreadyMember {

    }

    @Override
    public void ApplyJoinTeam(String userId, String teamId) throws NotYetDeal, AlreadyMember {

    }

    @Override
    public void dealInvite(String teamId, String userId) throws AlreadyDeal, OperationFail {

    }

    @Override
    public void dealApply(String teamId, String userId) throws AlreadyDeal, OperationFail {

    }

    @Override
    public statusUtoT checkStatus(String teamId, String userId) throws NotExist {
        return null;
    }


}
