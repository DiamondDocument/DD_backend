package diamondpick.dd_backend.Controller.zzy;

import diamondpick.dd_backend.Entity.zzy.Document;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.FileService;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private FileService fileService;

    private HashMap<String, String> imgTypeMap = new HashMap<>();

    String baseLocation = "C:/Users/18389/Desktop/doc_img/";
    static class CreateDocumentReq{
        String name;
        String userId;
        int authority;
        String fatherId;
    }
    @PostMapping(value = "/api/document/create")
    public HashMap<String,Object> CreateDocument(@RequestBody CreateDocumentReq req){
//        Document newDoc;
//        if((newDoc = documentService.newDoc(req.name, req.userId, req.authority, req.fatherId)) == null){
//            return response(-1,"documentId",null);
//        }
//        return  response(0, "documentId", newDoc.getId());
        return null;
    }
    static class CollectReq{
        String userId;
        String docId;
    }
    @PostMapping("/api/document/like")
    public HashMap<String,Object> collectDoc(@RequestBody CollectReq req){
        Response res = new Response();
        return res.set(documentService.collect(req.userId, req.docId));
    }
    @PostMapping("/api/document/dislike")
    public HashMap<String,Object> discollectDoc(@RequestBody CollectReq req){
        Response res = new Response();
        return res.set(documentService.discollect(req.userId, req.docId));
    }


    @PostMapping(value = "/api/document/img")
    public HashMap<String,Object> postImage(@RequestParam() MultipartFile file){
        Response res = new Response("url");
        if(fileService.saveFile( baseLocation + file.getOriginalFilename() ,file)){
            imgTypeMap.put(file.getOriginalFilename(), file.getContentType());
            return res.set(0, "http://localhost/api/document/img/" + file.getOriginalFilename());
        }
        return res.set(1, "上传错误");
    }
//    @GetMapping(value="/api/document/img/{filename}" , produces = MediaType.)
//    public byte[] getImage(@PathVariable String filename){
//        return  fileService.getFile(baseLocation + filename);
//    }

    @GetMapping(value="/api/document/img/{filename}")
    public @ResponseBody void getImage(@PathVariable String filename, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType(imgTypeMap.get(filename));
        response.getOutputStream().write(fileService.getFile(baseLocation + filename));
        return;
    }
}


