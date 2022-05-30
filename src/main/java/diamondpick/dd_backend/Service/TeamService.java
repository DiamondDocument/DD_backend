package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.Team.*;
import diamondpick.dd_backend.Exception.Illegal.TeamNameIllegal;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Tool.UserStatusToTeam;

import java.util.List;

public interface TeamService {
    /**该过程会新建一个空间并联动至团队*/
    public void newTeam(String name, String intro, String captainId)throws OtherFail, TeamNameIllegal;

    public void dismissTeam(String teamId, String captainId) throws OperationFail;

    /**包括队长和队员，注意user_member表只包含队员*/
    public List<User> findUserInTeam(String teamId)throws NotExist;

    public void modifyName(String teamId, String newName)throws Illegal, NotExist;
    public void modifyIntro(String teamId, String newIntro)throws Illegal, NotExist;

    public void removeMember(String teamId, String memberId) throws NotInTeam, OtherFail;

    /**
     * @throws AlreadyCaption 要转让的人已经是队长了
     * @throws NotInTeam 要转让的人不在团队中
     */
    public void transferRank(String teamId, String oldCaptainId, String newCaptainId)throws NotInTeam, AlreadyCaption, OtherFail;


    /**
     * @throws NotYetDeal 之前的申请或者邀请未处理
     * @throws AlreadyMember 已经在团队里
     * 需要发送通知
     */
    public void inviteMember(String teamId, String userId) throws NotYetDeal, AlreadyMember, OtherFail;

    /**
     * @throws NotYetDeal     之前的申请或者邀请未处理
     * @throws AlreadyMember  已经在团队里
     * 需要发送通知
     */
    public void applyJoinTeam(String userId, String teamId) throws NotYetDeal, AlreadyMember, OtherFail;

    /**
     * @throws NoDealTodo 没有待处理的申请或者邀请
     */
    public void dealInvite(String teamId, String userId, boolean isAgree)throws NoDealTodo, OtherFail;
    /**
     * @throws NoDealTodo 没有待处理的申请或者邀请
     */
    public void dealApply(String teamId, String userId, boolean isAgree)throws NoDealTodo, OtherFail;


    public UserStatusToTeam checkStatus(String teamId, String userId)throws OperationFail;

}
