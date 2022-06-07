package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Dao.TeamDealDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Entity.TeamDeal;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Exception.Team.*;
import diamondpick.dd_backend.Service.EmailService;
import diamondpick.dd_backend.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static diamondpick.dd_backend.Tool.UserStatusToTeam.*;

@RestController
public class TeamDealController {
    @Autowired
    TeamService teamService;
    @Autowired
    private EmailService emailService;
    @Autowired
    TeamDao teamDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TeamDealDao teamDealDao;
    @PostMapping("/api/team/invite")
    public Map<String, Object> invite(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String userId = remap.get("userId");
        try {
            if(teamDao.selectTeam(teamId)==null){
                ret.put("code",1);
                return ret;
            }
            if(userDao.selectUser(userId)==null){
                ret.put("code",2);
                return ret;
            }
            if(teamDealDao.selectDeal(teamId,userId)!=null){
                TeamDeal teamDeal = teamDealDao.selectDeal(teamId,userId);
                if(teamDeal.getDealStatus()!=0){
                    ret.put("code",3);
                    return ret;
                }
            }
            teamService.inviteMember(teamId,userId);
            User user = userDao.selectUser(userId);
            emailService.sendSimpleMail(user.getEmail(),"邀请加入团队","邀请加入团队"+teamId);
            ret.put("code",0);
            return ret;
        }
        catch (OtherFail e) {
            ret.put("code",-1);
            return ret;
        }   catch (AlreadyMember e) {
            ret.put("code",4);
            return ret;
        } catch (NotYetDeal e) {
            ret.put("code",3);
            return ret;
        }
    }
    @PostMapping("/api/team/apply")
    public Map<String, Object> apply(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String userId = remap.get("userId");
        try {
            if(teamDao.selectTeam(teamId)==null){
                ret.put("code",1);
                return ret;
            }
            if(userDao.selectUser(userId)==null){
                ret.put("code",2);
                return ret;
            }
            if(teamDealDao.selectDeal(teamId,userId)!=null){
                TeamDeal teamDeal = teamDealDao.selectDeal(teamId,userId);
                if(teamDeal.getDealStatus()!=0){
                    ret.put("code",3);
                    return ret;
                }
            }
            teamService.applyJoinTeam(userId,teamId);
            Team team = teamDao.selectTeam(teamId);
            User user = userDao.selectUser(team.getCaptainId());
            emailService.sendSimpleMail(user.getEmail(),"申请加入团队",userId+"申请加入团队");
            ret.put("code",0);
            return ret;
        }
        catch (OtherFail e) {
            ret.put("code",-1);
            return ret;
        }   catch (AlreadyMember e) {
            ret.put("code",4);
            return ret;
        } catch (NotYetDeal e) {
            ret.put("code",3);
            return ret;
        }
    }
    @PostMapping("/api/team/invite-deal")
    public Map<String, Object> inviteDeal(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String userId = remap.get("userId");
        boolean deal = Boolean.getBoolean(remap.get("deal"));
        try {
            if(teamDealDao.selectDeal(teamId,userId)==null){
                ret.put("code",1);
                return ret;
            }
            teamService.dealInvite(teamId,userId,deal);
            Team team = teamDao.selectTeam(teamId);
            User user = userDao.selectUser(team.getCaptainId());
            if(deal){
                emailService.sendSimpleMail(user.getEmail(),"邀请处理",userId+"同意加入团队");
            }
            else{
                emailService.sendSimpleMail(user.getEmail(),"邀请处理",userId+"拒绝加入团队");
            }
            ret.put("code",0);
            return ret;
        }
        catch (OtherFail e) {
            ret.put("code",-1);
            return ret;
        }   catch (NoDealTodo e) {
            ret.put("code",1);
            return ret;
        }
    }
    @PostMapping("/api/team/apply-deal")
    public Map<String, Object> applyDeal(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String userId = remap.get("userId");
        boolean deal = Boolean.getBoolean(remap.get("deal"));
        try {
            if(teamDealDao.selectDeal(teamId,userId)==null){
                ret.put("code",1);
                return ret;
            }
            teamService.dealApply(teamId,userId,deal);
            User user = userDao.selectUser(userId);
            if(deal){
                emailService.sendSimpleMail(user.getEmail(),"申请处理",teamId+"同意加入团队");
            }
            else{
                emailService.sendSimpleMail(user.getEmail(),"申请处理",teamId+"拒绝加入团队");
            }
            ret.put("code",0);
            return ret;
        }
        catch (OtherFail e) {
            ret.put("code",-1);
            return ret;
        }   catch (NoDealTodo e) {
            ret.put("code",1);
            return ret;
        }
    }
    @GetMapping(value="/api/team/is-apply")
    public Map<String, Object> isApply(@RequestParam String teamId,@RequestParam String userId) {
        Map<String,Object> ret = new HashMap<>();
        try{
            TeamDeal teamDeal = teamDealDao.selectDeal(teamId,userId);
            if(teamDeal==null){
                ret.put("code",3);
                return ret;
            }
            if(teamDeal.getDealType()!=2){
                ret.put("code",3);
                return ret;
            }
            if(teamDeal.getDealStatus()==0){
                ret.put("code",0);
                return ret;
            }
            if(teamDeal.getDealStatus()==1){
                ret.put("code",1);
                return ret;
            }
            if(teamDeal.getDealStatus()==2){
                ret.put("code",2);
            }
            else{
                ret.put("code",-1);
            }
            return ret;
        }catch (Exception e){
            ret.put("code",-1);
            return ret;
        }
    }
    @GetMapping(value="/api/team/is-invite")
    public Map<String, Object> isInvite(@RequestParam String teamId,@RequestParam String userId) {
        Map<String,Object> ret = new HashMap<>();
        try{
            TeamDeal teamDeal = teamDealDao.selectDeal(teamId,userId);
            if(teamDeal==null){
                ret.put("code",3);
                return ret;
            }
            if(teamDeal.getDealType()!=1){
                ret.put("code",3);
                return ret;
            }
            if(teamDeal.getDealStatus()==0){
                ret.put("code",0);
                return ret;
            }
            if(teamDeal.getDealStatus()==1){
                ret.put("code",1);
                return ret;
            }
            if(teamDeal.getDealStatus()==2){
                ret.put("code",2);
            }
            else{
                ret.put("code",-1);
            }
            return ret;
        }catch (Exception e){
            ret.put("code",-1);
            return ret;
        }
    }
    @GetMapping(value="/api/team/team-status")
    public Map<String, Object> teamStatus(@RequestParam String teamId,@RequestParam String userId) {
        Map<String,Object> ret = new HashMap<>();
        try{
            if(teamService.checkStatus(teamId,userId).equals(captain)){
                ret.put("code",0);
                ret.put("status",0);
                return ret;
            }
            else if(teamService.checkStatus(teamId,userId).equals(member)){
                ret.put("code",0);
                ret.put("status",1);
                return ret;
            }
            else if(teamService.checkStatus(teamId,userId).equals(applying)){
                ret.put("code",0);
                ret.put("status",2);
                return ret;
            }
            else if(teamService.checkStatus(teamId,userId).equals(inviting)){
                ret.put("code",0);
                ret.put("status",3);
                return ret;
            }
            else{
                ret.put("code",0);
                ret.put("status",4);
                return ret;
            }
        }catch (OperationFail e){
            ret.put("code",-1);
            return ret;
        }
    }
}
