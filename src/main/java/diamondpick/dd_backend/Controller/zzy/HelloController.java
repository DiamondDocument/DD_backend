package diamondpick.dd_backend.Controller.zzy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("api/hello")
    public String helloWorld(){
        return "hello,world!";
    }
}
