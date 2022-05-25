package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.yyh.Team;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Exception.Team.AlreadyCaption;
import diamondpick.dd_backend.Exception.Illegal.TeamNameIllegal;
import diamondpick.dd_backend.Exception.NotExist.TeamNotExist;
import diamondpick.dd_backend.Exception.Team.NotMember;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.Team.AlreadyMember;
import diamondpick.dd_backend.Exception.Team.NotYetDeal;

import java.util.ArrayList;

public interface TeamService {
    public void addTeam(String name, String intro, String captainId)throws OperationFail, TeamNameIllegal;
    public void dismissTeam(String teamId, String captainId) throws OperationFail;

    public Team selectTeamByTeamId(String teamId) throws TeamNotExist;

    //这里要包括团队和队长
    public ArrayList<User> selectUsersInTeam(String teamId)throws TeamNotExist, OperationFail;
    public void modifyName(String teamId)throws TeamNotExist, OperationFail;
    public void modifyIntro(String teamId)throws TeamNotExist, OperationFail;
    public void removeMember(String teamId, String captainId, String memberId) throws NotMember, OperationFail;
    public void transferRank(String teamId, String oldCaptainId, String newCaptainId)throws NotMember, AlreadyCaption, OperationFail;


    //申请邀请部分
    //申请或者邀请未处理前或者因为在团队里，则无法再次邀请或者申请
    public void InviteMember(String teamId, String userId) throws NotYetDeal, AlreadyMember;
    public void ApplyJoinTeam(String userId, String teamId) throws NotYetDeal, AlreadyMember;


    public void dealInvite(String teamId, String userId);

    public void dealApply(String teamId, String userId);

}
