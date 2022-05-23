package diamondpick.dd_backend.Controller.zzy;

import diamondpick.dd_backend.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@CrossOrigin
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    String baseLocation = "C:/Users/18389/Desktop/doc_img/";



    @PostMapping(value = "api/document/img")
    public HashMap<String,Object> postImage(@RequestParam()MultipartFile file){
        Response res = new Response("errno", "message", "data");
        Response data = new Response("url", "alt", "href");
        if(fileService.saveFile( baseLocation + file.getOriginalFilename() ,file)){
            return res.set(null, 0, null, data.set(null, "http://localhost/api/document/img/" + file.getOriginalFilename(), "失败？", "http://localhost/api/document/img/" + file.getOriginalFilename()));
        }
        return res.set(null, 1, "上传错误", null);
    }
    @GetMapping(value="/api/document/img/{filename}" , produces = "image/jpg")
    public byte[] getImage(@PathVariable String filename){
        return  fileService.getFile(baseLocation + filename);
    }


    @GetMapping(value="/api/avatar/{filename}" ,produces = "image/png")
    public byte[] getAvatar(@PathVariable String filename){
        return fileService.getFile(filename);
    }

    @GetMapping(value="/api/document/get/{filename}" )
    public byte[] getDocument(@PathVariable String filename){
        return fileService.getFile(filename);
    }


    @PostMapping("/api/avatar")
    public HashMap<String,String> postAvatar(@RequestParam("file")MultipartFile file, @RequestParam String path){
        if(fileService.saveFile(path,file)){
            return null;
        }
        return null;
    }





}
