package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.FileService;
import diamondpick.dd_backend.Tool.IdGenerator;
import diamondpick.dd_backend.Tool.JsonArray;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthService authService;

    @GetMapping("api/hello")
    public Map<String, Object> helloWorld(@RequestParam(required = false) String userId){
        Map<String, Object> ret = new HashMap<>();
        ret.put("giao", "一给我里giao");
        ret.put("message", "hello,world," + userId);
        ret.put("code", 0);
        return ret;
    }
    @PostMapping("api/post")
    public String post(@RequestBody Map<String, Object> req){
        return (String)req.get("content");
    }
    @PostMapping("api/file")
    public String file(@RequestParam MultipartFile file){
        return "获得文件"+file.getName();
    }

    @GetMapping("api/getarr")
    public Map<String, Object> getArr(){
        Response res = new Response("people");
        JsonArray arr = new JsonArray("name", "intro");
        arr.add("赵正阳", "一个buaaer");
        arr.add("giao哥", "一给我里给i奥");
        arr.add("V", "欢迎来到夜之城");
        return res.get(0,arr.get());
    }
    @PostMapping("api/body-and-formdata")
    public Map<String, Object> post(@RequestBody Map<String, Object> req, @RequestParam MultipartFile file){
        Response r = new Response("body","file");
        return r.get(0, req, file.getOriginalFilename());
    }

    @GetMapping("api/idgenerator")
    public Map<String, Object> testIdGenerator() throws Illegal {
        Response res = new Response("folder", "document", "message", "team");
        String folderId = idGenerator.generateId('f');
        String documentId = idGenerator.generateId('d');
        String messageId = idGenerator.generateId('m');
        String teamId = idGenerator.generateId('t');
        return res.get(0, folderId, documentId, messageId, teamId);
    }

    @GetMapping("api/update_auth_recur")
    public Map<String, Object> testUpdateAuthRecur() throws Illegal {
        Response res = new Response();
        fileService.updateAuthRecur("f000001", 1);
        return res.get(0);
    }

    @GetMapping("api/delete_permanently")
    public Map<String, Object> testDeletePermanently() throws OtherFail, NoAuth {
        Response res = new Response();
        fileService.deletePermanently("f000006", "user2");
        return res.get(0);
    }

    @GetMapping("api/test_check_auth")
    public Map<String, Object> testTestCheckAuth() throws OperationFail {
        Response res = new Response("auth1", "auth2", "auth3");
        int auth1 = authService.checkFileAuth("d000004", "user1");
        int auth2 = authService.checkFileAuth("d000004", "user2");
        int auth3 = authService.checkFileAuth("d000004", "user3");
        return res.get(0, auth1, auth2, auth3);
    }
}
