package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.Illegal.TeamNameIllegal;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Exception.Team.AlreadyCaption;
import diamondpick.dd_backend.Exception.Team.NotInTeam;
import diamondpick.dd_backend.Service.LocalFileService;
import diamondpick.dd_backend.Service.MessageService;
import diamondpick.dd_backend.Service.TeamService;
import diamondpick.dd_backend.Tool.JsonArray;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    TeamDao teamDao;
    @Autowired
    LocalFileService localFileService;
    @Autowired
    MessageService messageService;
    @Autowired
    UserDao userDao;
    @PostMapping("/api/team/create")
    public Map<String, Object> createTeam(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamName = remap.get("teamName");
        String teamIntroductory = remap.get("teamIntroductory");
        String userId = remap.get("userId");
        try {
            String teamId = teamService.newTeam(teamName,teamIntroductory,userId);
            ret.put("code",0);
            ret.put("teamId",teamId);
            return ret;
        }
        catch (TeamNameIllegal teamNameIllegal){
            ret.put("code",1);
            return ret;
        }
        catch (OtherFail o){
            ret.put("code",-1);
            return ret;
        }
    }
    @PostMapping("/api/team/dismiss")
    public Map<String, Object> dismissTeam(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String userId = remap.get("userId");
        try {
            Team team = teamDao.selectTeam(teamId);
            if(!team.getCaptainId().equals(userId)){
                ret.put("code",1);
            }else{
                teamService.dismissTeam(teamId,userId);
                ret.put("code",0);
            }
            return ret;
        }
        catch (Exception e) {
            ret.put("code",-1);
            return ret;
        }
    }
    @PostMapping("/api/team/modify/name")
    public Map<String, Object> modifyTeamName(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String newName = remap.get("newName");
        try {
            teamService.modifyName(teamId,newName);
            ret.put("code",0);
            return ret;
        }
        catch (Illegal e) {
            ret.put("code",-1);
            return ret;
        }
        catch (NotExist n){
            ret.put("code",1);
            return ret;
        }
    }
    @PostMapping("/api/team/modify/avatar")
    public Map<String, Object> modifyAvatar(@RequestParam MultipartFile file, @RequestParam String teamId){
        try {
            localFileService.saveTeamAvatar(teamId,file);
            return new Response().get(0);
        }catch (OtherFail e){
            return new Response().get(-1);
        }
        catch (NotExist n){
            return new Response().get(1);
        }
    }
    @GetMapping(value="/api/team/get-avatar")
    public Map<String, Object> getAvatar(@RequestParam String teamId) {
        Response res = new Response("url");
        try{
            return res.get(0, localFileService.getTeamAvatarUrl(teamId));
        }catch (Exception e){
            return res.get(-1);
        }
    }
    @PostMapping("/api/team/modify/introduction")
    public Map<String, Object> modifyTeamIntroduction(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String newIntro = remap.get("newIntro");
        try {
            teamService.modifyIntro(teamId,newIntro);
            ret.put("code",0);
            return ret;
        }
        catch (Illegal e) {
            ret.put("code",-1);
            return ret;
        }
        catch (NotExist n){
            ret.put("code",1);
            return ret;
        }
    }
    @GetMapping(value="/api/team/information")
    public Map<String, Object> getInformation(@RequestParam String teamId) {
        Map<String,Object> ret = new HashMap<>();
        try{
            Team team = teamDao.selectTeam(teamId);
            if(team==null){
                ret.put("code",1);
                return ret;
            }
            ret.put("code",0);
            ret.put("name",team.getName());
            ret.put("intro",team.getIntro());
            return ret;
        }catch (Exception e){
            ret.put("code",-1);
            return ret;
        }
    }
    @GetMapping(value="/api/team/member")
    public Map<String, Object> getMember(@RequestParam String teamId) {
        Map<String,Object> ret = new HashMap<>();
        try{
            Team team = teamDao.selectTeam(teamId);
            if(team==null){
                ret.put("code",1);
                return ret;
            }
            List<User> users = userDao.selectMember(teamId);
            JsonArray members = new JsonArray("userId", "name", "rank", "url","intro");
            String captainId = team.getCaptainId();
            User captain = userDao.selectUser(captainId);
            members.add(captainId, captain.getNickname(), "??????", localFileService.getUserAvatarUrl(captainId),captain.getIntro());
            for(User user : users){
                members.add(user.getUserId(), user.getNickname(), "??????", localFileService.getUserAvatarUrl(user.getUserId()),user.getIntro());
            }
            ret.put("code",0);
            ret.put("member", members.get());
            return ret;
        }catch (Exception e){
            ret.put("code",-1);
            return ret;
        }
    }
    @PostMapping("/api/team/remove")
    public Map<String, Object> remove(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String captainId = remap.get("captainId");
        String memberId = remap.get("memberId");
        try {
            teamService.removeMember(teamId,memberId);
            String captainName = userDao.selectUser(captainId).getNickname();
            String teamName = teamDao.selectTeam(teamId).getName();
            messageService.newMsg(captainId,captainName+" ???????????????????????? "+teamName,memberId);
            ret.put("code",0);
            return ret;
        } catch (NotInTeam e) {
            ret.put("code",1);
            return ret;
        } catch (OperationFail e) {
            ret.put("code",-1);
            return ret;
        }
    }
    @PostMapping("/api/team/transfer")
    public Map<String, Object> transfer(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String oldCaptainId = remap.get("oldCaptainId");
        String newCaptainId = remap.get("newCaptainId");
        try {
            teamService.transferRank(teamId,oldCaptainId,newCaptainId);
            ret.put("code",0);
            return ret;
        }
        catch (OtherFail e) {
            ret.put("code",-1);
            return ret;
        } catch (NotInTeam e) {
            ret.put("code",2);
            return ret;
        } catch (AlreadyCaption e) {
            ret.put("code",3);
            return ret;
        }
    }
    @PostMapping("/api/team/leave")
    public Map<String, Object> leave(@RequestBody Map<String, String> remap){
        Map<String,Object> ret = new HashMap<>();
        String teamId = remap.get("teamId");
        String userId = remap.get("userId");
        try {
            teamDao.deleteMember(teamId,userId);
            String memberName = userDao.selectUser(userId).getNickname();
            String captainId = teamDao.selectTeam(teamId).getCaptainId();
            messageService.newMsg(userId,memberName+" ???????????????",captainId);
            ret.put("code",0);
            return ret;
        }
        catch (Exception e) {
            ret.put("code",-1);
            return ret;
        }
    }
}
