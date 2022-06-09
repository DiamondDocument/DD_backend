package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.*;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Entity.TeamDeal;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Exception.Duplicate.DuplicateId;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.Illegal.NameIllegal;
import diamondpick.dd_backend.Exception.Illegal.TeamNameIllegal;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Exception.Team.*;
import diamondpick.dd_backend.Service.*;
import diamondpick.dd_backend.Tool.IdGenerator;
import diamondpick.dd_backend.Tool.UserStatusToTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeamImp implements TeamService {
    @Autowired
    private ConstraintService constraintService;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    MessageService messageService;
    @Autowired
    private TeamDao teamDao;
    @Autowired
    private SpaceDao spaceDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TeamDealDao teamDealDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private FolderDao folderDao;
    @Override
    public String newTeam(String name, String intro, String captainId) throws OtherFail, TeamNameIllegal {
        if(userDao.selectUser(captainId)==null) throw new OtherFail();
        try{
            constraintService.checkName(name);
        }catch (Exception e){
            throw new TeamNameIllegal();
        }
        try {
            constraintService.checkIntro(intro);
        }catch (Exception e){
            throw new OtherFail();
        }
        spaceDao.insertSpace();
        try{
            String newId = idGenerator.generateId('t');
            teamDao.insertTeam(newId, name,intro,captainId,spaceDao.selectSpace());
            return newId;
        }catch (Exception e){
            e.printStackTrace();
            spaceDao.deleteSpace(spaceDao.selectSpace());
            throw new OtherFail();
        }
    }

    @Override
    public void dismissTeam(String teamId, String captainId) throws OperationFail {
        try{
            int spaceId = teamDao.selectTeam(teamId).getSpaceId();
            documentDao.deleteDocInSpace(spaceId);
            folderDao.deleteDocInSpace(spaceId);
            teamDao.deleteAllMember(teamId);
            teamDealDao.deleteAllDeal(teamId);
            teamDao.deleteTeam(teamId);
            spaceDao.deleteSpace(spaceId);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            throw new OperationFail();
        }
    }

    @Override
    public List<User> findUserInTeam(String teamId) throws NotExist {
        List<User> users;
        try {
             users = userDao.selectMember(teamId);
        }catch (Exception e){
            e.printStackTrace();
            throw new NotExist();
        }
        return users;
    }

    @Override
    public void modifyName(String teamId, String newName) throws Illegal, NotExist {
        if(teamDao.selectTeam(teamId)==null)
            throw new NotExist();
        constraintService.checkName(newName);
        try{
            teamDao.updateTeam(teamId,"name",newName);
        }catch (DataIntegrityViolationException e){
            throw new Illegal();
        }
    }

    @Override
    public void modifyIntro(String teamId, String newIntro) throws Illegal, NotExist {
        if(teamDao.selectTeam(teamId)==null)
            throw new NotExist();
        constraintService.checkIntro(newIntro);
        try{
            teamDao.updateTeam(teamId,"intro",newIntro);
        }catch (DataIntegrityViolationException e){
            throw new Illegal();
        }
    }

    @Override
    public void removeMember(String teamId, String memberId) throws NotInTeam, OtherFail {
        if(teamDao.selectTeam(teamId)==null)
            throw new OtherFail();
        List<User> users = userDao.selectMember(teamId);
        int flag = 0;
        for(User user:users){
            if(user.getUserId().equals(memberId)){
                flag = 1;
                break;
            }
        }
        if(flag==0)
            throw new NotInTeam();
        try {
            teamDao.deleteMember(teamId,memberId);
        }
        catch (Exception e){
            throw new OtherFail();
        }
    }

    @Override
    public void transferRank(String teamId, String oldCaptainId, String newCaptainId) throws NotInTeam, AlreadyCaption, OtherFail {
        if(teamDao.selectTeam(teamId)==null)
            throw new OtherFail();
        Team team = teamDao.selectTeam(teamId);
        if(newCaptainId.equals(team.getCaptainId()))
            throw new AlreadyCaption();
        List<User> users = userDao.selectMember(teamId);
        int flag = 0;
        for(User user:users){
            if(user.getUserId().equals(newCaptainId)){
                flag = 1;
                break;
            }
        }
        if(flag==0)
            throw new NotInTeam();
        try{
            teamDao.updateTeam(teamId,"captain_id",newCaptainId);
            teamDao.deleteMember(teamId,newCaptainId);
            teamDao.insertMember(teamId, oldCaptainId);
        }catch (DataIntegrityViolationException e){
            throw new OtherFail();
        }
    }

    @Override
    public void inviteMember(String teamId, String userId) throws NotYetDeal, AlreadyMember, OtherFail {
        if(teamDealDao.selectDeal(teamId,userId)!=null){
            if(teamDealDao.selectDeal(teamId,userId).getDealStatus() == 0)
                throw new NotYetDeal();
        }
        List<User> users = userDao.selectMember(teamId);
        int flag = 0;
        for(User user:users){
            if(user.getUserId().equals(userId)){
                flag = 1;
                break;
            }
        }
        if(flag==1)
            throw new AlreadyMember();
        try{
            teamDealDao.insertDeal(teamId,userId,1);
            messageService.newTeamDealMsg(teamDealDao.selectDeal(teamId, userId).getDealId());
        }catch (OperationFail e){
            throw new OtherFail();
        }
    }

    @Override
    public void applyJoinTeam(String userId, String teamId) throws NotYetDeal, AlreadyMember, OtherFail {
        if(teamDealDao.selectDeal(teamId,userId)!=null){
            if(teamDealDao.selectDeal(teamId,userId).getDealStatus() == 0)
                throw new NotYetDeal();
        }
        List<User> users = userDao.selectMember(teamId);
        int flag = 0;
        for(User user:users){
            if(user.getUserId().equals(userId)){
                flag = 1;
                break;
            }
        }
        if(flag==1)
            throw new AlreadyMember();
        try{
            teamDealDao.insertDeal(teamId,userId,2);
            messageService.newTeamDealMsg(teamDealDao.selectDeal(teamId, userId).getDealId());
        }catch (OperationFail e){
            throw new OtherFail();
        }
    }

    @Override
    public void dealInvite(String teamId, String userId, boolean isAgree) throws NoDealTodo, OtherFail {
        TeamDeal teamDeal = teamDealDao.selectDeal(teamId,userId);
        if(teamDeal==null)
            throw new NoDealTodo();
        else if(teamDeal.getDealType()!=1)
            throw new NoDealTodo();
        else if(teamDeal.getDealStatus() != 0)
            throw new NoDealTodo();
        try{
            if(isAgree){
                teamDealDao.updateStatus(teamDeal.getDealId(),1);
                teamDao.insertMember(teamId,userId);
            }
            else
                teamDealDao.updateStatus(teamDeal.getDealId(),2);
            messageService.newTeamDealMsg(teamDealDao.selectDeal(teamId, userId).getDealId());
        }catch (OperationFail e){
            throw new OtherFail();
        }
    }

    @Override
    public void dealApply(String teamId, String userId, boolean isAgree) throws NoDealTodo, OtherFail {
        TeamDeal teamDeal = teamDealDao.selectDeal(teamId,userId);
        if(teamDeal==null)
            throw new NoDealTodo();
        else if(teamDeal.getDealType()!=2)
            throw new NoDealTodo();
        else if(teamDeal.getDealStatus() != 0)
            throw new NoDealTodo();
        try{
            if(isAgree){
                teamDealDao.updateStatus(teamDeal.getDealId(),1);
                teamDao.insertMember(teamId,userId);
            }
            else
                teamDealDao.updateStatus(teamDeal.getDealId(),2);
            messageService.newTeamDealMsg(teamDealDao.selectDeal(teamId, userId).getDealId());
        }catch (OperationFail e){
            throw new OtherFail();
        }
    }

    @Override
    public UserStatusToTeam checkStatus(String teamId, String userId) throws OperationFail {
        TeamDeal teamDeal = teamDealDao.selectDeal(teamId,userId);
        UserStatusToTeam userStatusToTeam;
        if(teamDao.selectTeam(teamId).getCaptainId().equals(userId)){
            userStatusToTeam = UserStatusToTeam.captain;
            return userStatusToTeam;
        }
        List<User> users = userDao.selectMember(teamId);
        int flag = 0;
        for(User user:users){
            if(user.getUserId().equals(userId)){
                flag = 1;
                break;
            }
        }
        if(flag==1){
            userStatusToTeam = UserStatusToTeam.member;
            return userStatusToTeam;
        }
        if(teamDeal!=null && teamDeal.getDealType()==1){
            userStatusToTeam = UserStatusToTeam.inviting;
            return userStatusToTeam;
        }
        if(teamDeal!=null && teamDeal.getDealType()==2){
            userStatusToTeam = UserStatusToTeam.applying;
            return userStatusToTeam;
        }
        userStatusToTeam = UserStatusToTeam.other;
        return userStatusToTeam;
    }
}
