package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.*;
import diamondpick.dd_backend.Entity.*;
import diamondpick.dd_backend.Exception.Duplicate.DuplicateId;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.Illegal.NameIllegal;
import diamondpick.dd_backend.Exception.Illegal.PwdIllegal;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Exception.User.PwdError;
import diamondpick.dd_backend.Service.*;
import diamondpick.dd_backend.Tool.JsonArray;
import diamondpick.dd_backend.Tool.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private FileService fileService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ConstraintService constraintService;
    @Autowired
    private LocalFileService localFileService;

    @Resource
    private TemplateEngine templateEngine;

    @GetMapping("/api/user/team")
    public Map<String, Object> userTeam(@RequestParam String userId) {
        Response res = new Response("teams");
        JsonArray arr = new JsonArray("name", "intro", "teamId");
        try{
            List<Team> teams = userService.selectTeams(userId);
            for(Team t : teams){
                arr.add(t.getName(), t.getIntro(), t.getTeamId());
            }
            return res.get(0, arr.get());
        }catch (Exception e){
            return res.get(-1);
        }
    }

    @GetMapping("/api/login")
    public Map<String, Object> login(@RequestParam(required = false) String email, @RequestParam(required = false) String userId, @RequestParam String pwd) {
        Response res = new Response("userId", "nickName");
        User loginUser;
        try{
            loginUser = userService.login(userId, email, pwd);
            return res.get(0, loginUser.getUserId(), loginUser.getNickname());
        }catch (NotExist e){
            return res.get(1);
        }catch (PwdError e){
            return res.get(2);
        }catch (OtherFail e){
            return res.get(-1);
        }
    }

    @PostMapping("/api/register")
    public Map<String, Object> register(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String userId = re_map.get("userId");
        String password = re_map.get("pwd");
        String nickname = re_map.get("nickName");
        String email = re_map.get("email");
        String code = re_map.get("verificationCode");
        try{
            emailService.checkVerification(email, code);
        }catch (OperationFail e){
            return res.get(3);
        }
        try{
            userService.newUser(userId, nickname, password, email);
            return res.get(0);
        }catch (NameIllegal e){
            return res.get(2);
        }catch (PwdIllegal e){
            return res.get(4);
        }catch (DuplicateId e){
            return res.get(1);
        }catch (OperationFail e){
            return res.get(-1);
        }

    }

    @GetMapping("/api/user/send-identifying")
    public Map<String, Object> identifying(@RequestParam(name = "email") String email) {
        Response res = new Response();
        try{
            emailService.sendVerification(email);
            return res.get(0);
        }catch (OperationFail e){
            return res.get(-1);
        }
    }

    @GetMapping("/api/user/register/check-id")
    public Map<String, Object> checkID(@RequestParam String userId) {
        Response res = new Response();
        if(userDao.selectUser(userId) != null)return res.get(1);
        return res.get(0);
    }

    @GetMapping("/api/user/information")
    public Map<String, Object> getUserInformation(@RequestParam String userId) {
        Response res = new Response("nickName", "email", "introduction");
        User user = userDao.selectUser(userId);
        if(user == null)return res.get(1);
        return res.get(0, user.getNickname(), user.getEmail(), user.getIntro());
    }

    @PostMapping("/api/user/modify/password")
    public Map<String, Object> modifyPassword(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String id = re_map.get("userId");
        String newPass = re_map.get("newPwd");
        String oldPass = re_map.get("oldPwd");
        try{
            User user = userDao.selectUser(id);
            if(user == null)throw new OtherFail();
            if (!user.getPassword().equals(oldPass)) {
                return res.get(1);
            }
            constraintService.checkPassword(newPass);
            userDao.updateUser(id, "password", newPass);
            return res.get(0);
        }catch (Illegal | OtherFail e){
            return res.get(-1);
        }
    }

    @PostMapping("/api/user/modify/email")
    public Map<String, Object> modifyEmail(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String id = re_map.get("userId");
        String newEmail = re_map.get("newEmail");
        String code = re_map.get("verificationCode");
        try{
            emailService.checkVerification(newEmail, code);
        }catch (OperationFail e){
            return res.get(1);
        }
        try{
            User user = userDao.selectUser(id);
            if(user == null)throw new OperationFail();
            userDao.updateUser(id, "email", newEmail);
            return res.get(0);
        }catch(OperationFail e){
            return res.get(-1);
        }
    }

    @PostMapping("/api/user/modify/nickname")
    public Map<String, Object> modifyNickname(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String id = re_map.get("userId");
        String newName = re_map.get("newNick");
        try{
            User user = userDao.selectUser(id);
            if(user == null)throw new OperationFail();
            constraintService.checkName(newName);
            userDao.updateUser(id, "nickname", newName);
            return res.get(0);
        }catch(OperationFail e){
            return res.get(-1);
        }
    }


    @PostMapping("/api/user/modify/introduction")
    public Map<String, Object> modifyIntro(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String id = re_map.get("userId");
        String intro = re_map.get("newIntro");
        try{
            User user = userDao.selectUser(id);
            if(user == null)throw new OperationFail();
            constraintService.checkIntro(intro);
            userDao.updateUser(id, "intro", intro);
            return res.get(0);
        }catch(OperationFail e){
            return res.get(-1);
        }
    }

    @PostMapping("/api/user/modify/avatar")
    public Map<String, Object> modifyAvatar(@RequestParam MultipartFile file, @RequestParam String userId){
        try {
            localFileService.saveUserAvatar(userId, file);
            return new Response().get(0);
        }catch (OperationFail e){
            return new Response().get(-1);
        }
    }

    @GetMapping(value="/api/user/avatar/{userId}")
    public @ResponseBody void getAvatar(@PathVariable String userId, HttpServletResponse response) {
//        //todo
//        try{
//            response.reset();
//            response.setContentType(fileService.getAvatarContentType(userId));
//            response.getOutputStream().write(fileService.getAvatar(userId));
//        }catch(Exception e){
//            e.printStackTrace();
//            //加载失败
//        }
    }

}