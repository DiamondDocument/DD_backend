package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Entity.TeamMember;
import diamondpick.dd_backend.Entity.TeamMessage;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Service.MailService;
import diamondpick.dd_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserController userController;

    @Resource
    private TemplateEngine templateEngine;

    @GetMapping("/api/login")
    public Map<String, Object> login(@RequestParam(name = "email")String email, @RequestParam(name = "id")String userId, @RequestParam(name = "pwd")String password){
        Map<String, Object> response = new HashMap<>();
        response.put("error",0);
        response.put("nickName",null);
        User loginUser;
        if(password == null){
            response.put("error", 2);
            return response;
        }
        if(userId != null){
            loginUser = userService.selectUserByUserId(userId);
            if(loginUser == null){
                response.put("error", 1);
                return response;
            }
            if(!loginUser.getPassword().equals(password)){
                response.put("error", 2);
                return response;
            }
            response.put("nickName", loginUser.getNickname());
        }
        else if(email != null){
            loginUser = userService.selectUserByEmail(email);
            if(loginUser == null){
                response.put("error", 1);
                return response;
            }
            if(!loginUser.getPassword().equals(password)){
                response.put("error", 2);
                return response;
            }
            response.put("nickName", loginUser.getNickname());
        }
        else{
            response.put("error", -1);
            return response;
        }
        return response;
    }


    @PostMapping("/api/register")
    public RegisterRes register(@RequestBody RegisterReq request) {
        RegisterRes response = new RegisterRes(0);
        if(request.userId == null && request.password == null)
            return new RegisterRes(-1);
        if(!userService.isLegalUserId(request.userId))
            return new RegisterRes(4);
        if(!userService.isLegalPassword(request.password))
            return new RegisterRes(1);
        if(request.nickname != null && !userService.isLegalNickname(request.nickname))
            return new RegisterRes(4);
        if(request.email != null && !userService.isLegalEmail(request.email))
            return new RegisterRes(3);
        if(userService.isUserIdExist(request.userId))
            return new RegisterRes(2);
        if(userService.isEmailExist(request.email))
            return new RegisterRes(2);
        User newUser = new User();
        newUser.setUserId(request.userId);
        newUser.setPassword(request.password);
        if(request.email != null)
            newUser.setUser_email(request.email);
        if(request.nickname != null)
            newUser.setNickname(request.nickname);
        if(!userService.addUser(newUser)) return new RegisterRes(-1);
        return new RegisterRes(0);
    }

    @GetMapping("/api/user/send-identifying")
    public Map<String,Object> identifying(@RequestParam(name = "email")String email){
        Map<String,Object> map = new HashMap<>();
        if(email == null || !userService.isLegalEmail(email)){
            map.put("error",1);
            map.put("message","邮箱格式错误");
            return map;
        }
        Random random = new Random();
        String identifying="";
        for (int i=0;i<6;i++){
            identifying+=random.nextInt(10);
        }
        mailService.sendSimpleMail(email,"注册验证码",identifying);
        map.put("error",0);
        map.put("message",identifying);
        return map;
    }

    @GetMapping("/api/user/register/check-id")
    public Map<String,Object> checkID(@RequestParam(name = "id")String id){
        Map<String,Object> map = new HashMap<>();
        if(userService.selectUserByUserId(id)==null){
            map.put("error",1);
            map.put("message","用户不存在");
        }
        else{
            map.put("error",0);
            map.put("message","用户存在");
        }
        return map;
    }

    @GetMapping("/api/user/basic")
    public getUserInformationRes getUserInformation(@RequestParam(name = "id") String id) {
        User user = userService.selectUserByUserId(id);
        if(user == null)
            return new getUserInformationRes(1);
        return new getUserInformationRes(0,user.getNickname(),user.getGender(),
                    user.getUser_introductory(),user.getUser_email());
    }
}



class RegisterRes {
    private Integer error;

    public RegisterRes(Integer error) {
        this.error = error;
    }

    public Integer getError() {
        return error;
    }
}
class RegisterReq {
    String userId;
    String nickname;
    String password;
    String email;
}
class getUserInformationRes {
    private Integer error;
    private String nickname;
    private String gender;
    private String userIntroductory;
    private String email;
    public getUserInformationRes(Integer error, String nickname, String gender, String userIntroductory, String email) {
        this.error = error;
        this.nickname = nickname;
        this.gender = gender;
        this.userIntroductory = userIntroductory;
        this.email = email;
    }
    public getUserInformationRes(Integer error) {
        this.error = error;
    }
}
