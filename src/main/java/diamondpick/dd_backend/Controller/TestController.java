package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Exception.Illegal.Illegal;
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
        Response res = new Response("folder", "document", "message");
        String folderId = idGenerator.generateId('f');
        String documentId = idGenerator.generateId('d');
        String messageId = idGenerator.generateId('m');
        return res.get(0, folderId, documentId, messageId);
    }
}
