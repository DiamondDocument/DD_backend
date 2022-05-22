package diamondpick.dd_backend.Controller.yyh;

import diamondpick.dd_backend.Entity.yyh.User;
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

    @Resource
    private TemplateEngine templateEngine;

    @RequestMapping(value = "/api/login", params = {"email", "userId", "pwd"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> login(@RequestParam String email, @RequestParam String userId, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        User loginUser;
        if (password == null) {
            response.put("code", 2);
            response.put("name", null);
            response.put("nickName", null);
            return response;
        }
        if (userId != null) {
            loginUser = userService.selectUserByUserId(userId);
            if (loginUser == null) {
                response.put("code", 1);
                response.put("name", null);
                response.put("nickName", null);
                return response;
            }
            if (!loginUser.getPassword().equals(password)) {
                response.put("code", 2);
                response.put("name", null);
                response.put("nickName", null);
                return response;
            }
            response.put("code", 0);
            response.put("name", userId);
            response.put("nickName", loginUser.getNickname());
        } else if (email != null) {
            loginUser = userService.selectUserByEmail(email);
            if (loginUser == null) {
                response.put("code", 1);
                response.put("name", null);
                response.put("nickName", null);
                return response;
            }
            if (!loginUser.getPassword().equals(password)) {
                response.put("code", 2);
                response.put("name", null);
                response.put("nickName", null);
                return response;
            }
            response.put("code", 0);
            response.put("name", loginUser.getUser_id());
            response.put("nickName", loginUser.getNickname());
        } else {
            response.put("code", -1);
            response.put("name", null);
            response.put("nickName", null);
            return response;
        }
        return response;
    }


    @PostMapping("/api/register")
    public Map<String, Object> register(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String userId = re_map.get("userId");
        String password = re_map.get("password");
        String nickname = re_map.get("nickname");
        String email = re_map.get("email");
        if (userId == null && password == null) {
            map.put("code", -1);
            return map;
        }
        if (!userService.isLegalUserId(userId)) {
            map.put("code", -1);
            return map;
        }
        if (!userService.isLegalPassword(password)) {
            map.put("code", -1);
            return map;
        }
        if (nickname != null && !userService.isLegalNickname(nickname)) {
            map.put("code", -1);
            return map;
        }
        if (email != null && !userService.isLegalEmail(email)) {
            map.put("code", -1);
            return map;
        }
        if (userService.isUserIdExist(userId)) {
            map.put("code", -1);
            return map;
        }
        if (userService.isEmailExist(email)) {
            map.put("code", -1);
            return map;
        }
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setPassword(password);
        if (email != null)
            newUser.setUser_email(email);
        if (nickname != null)
            newUser.setNickname(nickname);
        if (!userService.addUser(newUser)) {
            map.put("code", -1);
            return map;
        }
        map.put("code", 0);
        return map;
    }

    @GetMapping("/api/user/send-identifying")
    public Map<String, Object> identifying(@RequestParam(name = "email") String email) {
        Map<String, Object> map = new HashMap<>();
        if (email == null || !userService.isLegalEmail(email)) {
            map.put("code", 1);
            map.put("identifyingCode", null);
            return map;
        }
        Random random = new Random();
        String identifying = "";
        for (int i = 0; i < 5; i++) {
            identifying += random.nextInt(10)+'0';
        }
        mailService.sendSimpleMail(email, "注册验证码", identifying);
        map.put("error", 0);
        map.put("identifyingCode", identifying);
        return map;
    }

    @GetMapping("/api/user/register/check-id")
    public Map<String, Object> checkID(@RequestParam(name = "id") String id) {
        Map<String, Object> map = new HashMap<>();
        if (userService.selectUserByUserId(id) == null) {
            map.put("code", 1);
        } else {
            map.put("code", 0);
        }
        return map;
    }

    @GetMapping("/api/user/information")
    public Map<String, Object> getUserInformation(@RequestParam(name = "id") String id) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.selectUserByUserId(id);
        if (user == null) {
            map.put("code", 1);
            map.put("nickName", null);
            map.put("email", null);
            map.put("introduction", null);
            return map;
        }
        map.put("code", 0);
        map.put("nickName", user.getNickname());
        map.put("email", user.getUser_email());
        map.put("introduction", user.getUser_introductory());
        return map;
    }

    @PostMapping("/api/user/modify/password ")
    public Map<String, Object> modifyPassword(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        String newPass = re_map.get("newPwd");
        String oldPass = re_map.get("oldPwd");
        User user = userService.selectUserByUserId(id);
        if (user == null) {
            map.put("code", -1);
            return map;
        }
        String pwd = user.getPassword();
        if (!pwd.equals(oldPass)) {
            map.put("code", 1);
            return map;
        }
        user.setPassword(newPass);
        map.put("code", 0);
        return map;
    }

    @PostMapping("/api/user/modify/email ")
    public Map<String, Object> modifyEmail(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        String newEmail = re_map.get("newEmail");
        User user = userService.selectUserByUserId(id);
        if (user == null) {
            map.put("code",1);
            return map;
        }
        user.setUser_email(newEmail);
        map.put("code", 0);
        return map;
    }
    @PostMapping("/api/user/modify/nickname ")
    public Map<String, Object> modifyNickname(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        String newNick = re_map.get("newNick");
        User user = userService.selectUserByUserId(id);
        if (user == null) {
            map.put("code",1);
            return map;
        }
        user.setNickname(newNick);
        map.put("code", 0);
        return map;
    }
    @PostMapping("/api/user/modify/introduction ")
    public Map<String, Object> modifyIntro(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        String newIntro = re_map.get("newIntro");
        User user = userService.selectUserByUserId(id);
        if (user == null) {
            map.put("code",1);
            return map;
        }
        user.setUser_introductory(newIntro);
        map.put("code", 0);
        return map;
    }
    @PostMapping("/api/user/team ")
    public Map<String, Object> userTeam(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        User user = userService.selectUserByUserId(id);
        if (user == null) {
            map.put("code",1);
            return map;
        }
        map.put("code", 0);
        return map;
    }
}
