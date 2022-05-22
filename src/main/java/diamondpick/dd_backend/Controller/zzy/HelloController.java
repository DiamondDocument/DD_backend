package diamondpick.dd_backend.Controller.zzy;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

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
}
