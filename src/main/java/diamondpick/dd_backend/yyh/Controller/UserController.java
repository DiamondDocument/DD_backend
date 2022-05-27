package diamondpick.dd_backend.yyh.Controller;

import diamondpick.dd_backend.yyh.Dao.UserDao;
import diamondpick.dd_backend.yyh.Entity.Team;
import diamondpick.dd_backend.Exception.NotExist.UserNotExist;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.zzy.Response;
import diamondpick.dd_backend.yyh.Entity.User;
import diamondpick.dd_backend.Service.MailService;
import diamondpick.dd_backend.Service.UserService;
import diamondpick.dd_backend.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserDao userDao;

    @Resource
    private TemplateEngine templateEngine;

    //-------------------------------------------------
    @GetMapping("/api/user/team")
    public Map<String, Object> userTeam(@RequestParam String userId) {
        Response res = new Response("teams");
        ArrayList<Map<String, Object>> arr = new ArrayList<>();
        Response res2 = new Response("name", "intro", "teamId");
        try{
            AbstractList<Team> teams = userService.selectTeams(userId);
            for(Team t : teams){
                arr.add(res2.get(0, t.getTeamName(), t.getTeamIntroductory(), t.getTeamID()));
            }
            return res.get(0, arr);
        }catch (Exception e){
            return res.get(-1);
        }
    }
    //-------------------------------------------------

    @GetMapping("/api/login")
    public @ResponseBody
    Map<String, Object> login(@RequestParam(required = false) String email, @RequestParam(required = false) String userId, @RequestParam String pwd) {
        Map<String, Object> response = new HashMap<>();
        Response res = new Response("userId", "nickName");
        if(email == null && userId == null || email != null && userId != null){
            return res.get(-1);
        }
        User loginUser;
        try{
            if(email != null){
                loginUser = userService.selectUserByEmail(email);
            }else{
                loginUser = userService.selectUserByUserId(userId);
            }
            if (!loginUser.getPassword().equals(pwd)) {
                return res.get(2);
            }
            return res.get(0, loginUser.getUserId(), loginUser.getNickname());
        }catch (UserNotExist e){
            return res.get(1);
        }catch (Exception e){
            return res.get(-1);
        }
    }

    @PostMapping("/api/register")
    public Map<String, Object> register(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        Map<String, Object> map = new HashMap<>();
        String userId = re_map.get("userId");
        String password = re_map.get("pwd");
        String nickname = re_map.get("nickName");
        String email = re_map.get("email");
        if (userId == null || password == null || nickname == null || email == null) {
            return res.get(-1);
        }
        if (!userService.isLegalUserId(userId)) {
            map.put("code", -1);
            return map;
        }
        if (!userService.isLegalPassword(password)) {
            map.put("code", -1);
            return map;
        }
        if (!userService.isLegalNickname(nickname)) {
            map.put("code", -1);
            return map;
        }
        if (!userService.isLegalEmail(email)) {
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
        newUser.setEmail(email);
        newUser.setNickname(nickname);
        try{
            userService.addUser(newUser);
        }catch (OperationFail e){
            map.put("code", -1);
        }
        map.put("code", 0);
        return map;
    }

    @GetMapping("/api/user/send-identifying")
    public Map<String, Object> identifying(@RequestParam(name = "email") String email) {
        Map<String, Object> map = new HashMap<>();
        if (!userService.isLegalEmail(email)) {
            map.put("code", -1);
            map.put("identifyingCode", null);
            return map;
        }
        Random random = new Random();
        String identifying = "";
        for (int i = 0; i < 5; i++) {
            identifying += random.nextInt(10);
        }
        mailService.sendSimpleMail(email, "注册验证码", identifying);
        map.put("error", 0);
        map.put("identifyingCode", identifying);
        return map;
    }

    @GetMapping("/api/user/register/check-id")
    public Map<String, Object> checkID(@RequestParam String userId) {
        Map<String, Object> map = new HashMap<>();
        try{
            userService.selectUserByUserId(userId);
            map.put("code", 0);
        }catch (UserNotExist e){
            map.put("code", 1);
        }catch (Exception e){
            map.put("code", -1);
        }
        return map;
    }

    @GetMapping("/api/user/information")
    public Map<String, Object> getUserInformation(@RequestParam String userId) {
        Map<String, Object> map = new HashMap<>();
        try{
            User user = userService.selectUserByUserId(userId);
            map.put("code", 0);
            map.put("nickName", user.getNickname());
            map.put("email", user.getEmail());
            map.put("introduction", user.getIntro());
        }catch (UserNotExist e){
            map.put("code", 1);
        }catch (Exception e){
            map.put("code", -1);
        }
        return map;
    }

    @PostMapping("/api/user/modify/password")
    public Map<String, Object> modifyPassword(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        String newPass = re_map.get("newPwd");
        String oldPass = re_map.get("oldPwd");
        try{
            User user = userService.selectUserByUserId(id);
            if (!user.getPassword().equals(oldPass)) {
                map.put("code", 1);
                return map;
            }
            userDao.updateUser(id, "password", newPass);
            map.put("code", 0);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", -1);
        }
        return map;
    }

    @PostMapping("/api/user/modify/email")
    public Map<String, Object> modifyEmail(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        String newEmail = re_map.get("newEmail");
        try{
            userService.selectUserByUserId(id);
            userDao.updateUser(id, "email", newEmail);
            map.put("code", 0);
        }catch (UserNotExist e){
            map.put("code", 1);
        }catch (Exception e){
            map.put("code", -1);
        }
        return map;
    }
    @PostMapping("/api/user/modify/nickname")
    public Map<String, Object> modifyNickname(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        String newNick = re_map.get("newNick");
        try{
            userService.selectUserByUserId(id);
            userDao.updateUser(id, "nickname", newNick);
            map.put("code", 0);
        }catch (UserNotExist e){
            map.put("code", 1);
        }catch (Exception e){
            map.put("code", -1);
        }
        return map;
    }
    @PostMapping("/api/user/modify/introduction")
    public Map<String, Object> modifyIntro(@RequestBody Map<String, String> re_map) {
        Map<String, Object> map = new HashMap<>();
        String id = re_map.get("userId");
        String newIntro = re_map.get("newIntro");
        try{
            userService.selectUserByUserId(id);
            userDao.updateUser(id, "intro", newIntro);
            map.put("code", 0);
        }catch (UserNotExist e){
            map.put("code", 1);
        }catch (Exception e){
            map.put("code", -1);
        }
        return map;
    }


    @PostMapping("/api/user/modify/avatar")
    public Map<String, Object> modifyAvatar(@RequestParam MultipartFile file, @RequestParam String userId){
        try {
            fileService.saveAvatar(userId, file);
        } catch (UserNotExist e){
            return new Response().get(1);
        } catch (Exception e){
            e.printStackTrace();
            return new Response().get(-1);
        }
        return new Response().get(0);
    }
    @GetMapping(value="/api/user/avatar/{userId}")
    public @ResponseBody void getAvatar(@PathVariable String userId, HttpServletResponse response) throws IOException {
        try{
            response.reset();
            response.setContentType(fileService.getAvatarContentType(userId));
            response.getOutputStream().write(fileService.getAvatar(userId));
        }catch(Exception e){
            e.printStackTrace();
            //加载失败
        }
    }

}
