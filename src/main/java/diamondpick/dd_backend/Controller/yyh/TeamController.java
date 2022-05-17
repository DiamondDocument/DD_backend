package diamondpick.dd_backend.Controller.yyh;


import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.TeamMessage;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Service.TeamService;
import diamondpick.dd_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TeamController {
    public boolean checkTeamName(String teamName){
        for(int i = 0;i < teamName.length();i++){
            if(teamName.charAt(i)==127 || teamName.charAt(i) <=31){
                return false;
            }
        }
        return true;
    }
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @PostMapping("/api/team/create")
    public Map<String,Object> teamCreate(@RequestBody Map<String,String> re_map){
        String teamName = re_map.get("teamName");
        String teamIntroductory = re_map.get("teamIntroductory");
        String userID = re_map.get("userID");
        Map<String,Object> map = new HashMap<>();
        try {
            User user = userService.selectUserByUserId(userID);
            if(user==null){
                map.put("error", 2);
                map.put("message", "队长id不存在");
            }
            else if(!checkTeamName(teamName)){
                map.put("error", 1);
                map.put("message", "团队名称不合规范");
            }
            else{
                TeamMessage teamMessage = new TeamMessage(teamName,userID,teamIntroductory);
                teamService.registerNewTeam(teamMessage);
                int teamID = teamMessage.getTeamID();
                TeamMember teamMember = new TeamMember(teamID,userID,"队长");
                teamService.registerNewMember(teamMember);
                map.put("error", 0);
                map.put("message", "团队创建成功");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("error",-1);
            map.put("message","其他错误");
        }
        return map;
    }
    @PostMapping("/api/team/join")
    public Map<String,Object> teamJoin(@RequestBody Map<String,String> re_map){
        int teamID = Integer.parseInt(re_map.get("teamID"));
        String userID = re_map.get("userID");
        Map<String,Object> map = new HashMap<>();
        try {
            User user = userService.selectUserByUserId(userID);
            TeamMessage teamMessage = teamService.selectTeamByTeamId(teamID);
            TeamMember teamMember1 = teamService.selectUserByUserIdInTeam(userID,teamID);
            if(user==null){
                map.put("error",1);
                map.put("message","用户不存在");
            }
            else if(teamMessage==null){
                map.put("error",2);
                map.put("message","团队不存在");
            }
            else if(teamMember1!=null){
                map.put("error",3);
                map.put("message","用户已加入团队");
            }
            else{
                TeamMember teamMember = new TeamMember(teamID,userID,"队员");
                teamService.registerNewMember(teamMember);
                map.put("error",0);
                map.put("message","加入团队成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("error",-1);
            map.put("message","其他错误");
        }
        return map;
    }
}