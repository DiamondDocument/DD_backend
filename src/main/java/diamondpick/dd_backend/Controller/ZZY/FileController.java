package diamondpick.dd_backend.Controller.ZZY;

import diamondpick.dd_backend.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;

@RestController
public class FileController {

    @Autowired
    FileService fileService;



    @GetMapping(value="/api/avatar/{filename}",produces = "image/png")
    public byte[] getAvatar(@PathVariable String filename){
        return fileService.getFile(filename);
    }

    @GetMapping(value="/api/document/get/{filename}")
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
