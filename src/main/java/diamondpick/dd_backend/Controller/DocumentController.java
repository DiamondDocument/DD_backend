package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.Document.SomoneEditing;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.LocalFileService;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.NotYetConnectedException;
import java.util.Map;


@RestController
public class DocumentController {
    DocumentDao documentDao;
    DocumentService documentService;
    AuthService authService;
    LocalFileService localFileService;

    @GetMapping("/api/document/edit")
    public Map<String, Object> requestEdit(@RequestParam String userId, @RequestParam String docId){
        Response res = new Response();
        try{
            documentService.keepEdit(userId, docId);
            return res.get(0);
        }catch (SomoneEditing e){
            return res.get(1);
        }catch (NoAuth e){
            return res.get(2);
        }catch (OperationFail e){
            return res.get(-1);
        }
    }
    @GetMapping("/api/document/quit-edit")
    public Map<String, Object> quitEdit(@RequestParam String userId, @RequestParam String docId){
        Response res = new Response();
        try{
            documentService.quitEdit(userId, docId);
            return res.get(0);
        }catch (OperationFail e){
            return res.get(-1);
        }
    }
    @GetMapping("/api/document/content")
    public Map<String, Object> getDoc(@RequestParam String userId, @RequestParam String docId){
        Response res = new Response("content");
        try{
            if(authService.checkFileAuth(docId, userId) < 2)return res.get(1);
            return res.get(0, localFileService.getDocument(docId));
        }catch (OperationFail e){
            return res.get(-1);
        }
    }
    @PostMapping("/api/document/save")
    public Map<String, Object> saveDoc(@RequestBody Map<String, String> req){
        String content = req.get("content");
        String docId = req.get("docId");
        String userId = req.get("userId");
        Response res = new Response();
        try{
            if(authService.checkFileAuth(docId, userId) < 4)return res.get(-1);
            localFileService.saveDocument(docId, content);
            return res.get(0);
        }catch (OperationFail e){
            return res.get(-1);
        }
    }
    @GetMapping("/api/document/export")
    public Map<String, Object> export(@RequestParam String docId){
        Response r = new Response("download");
        try{
            localFileService.htmlToDocx(docId);
            return r.get(0, localFileService.getDownloadUrl());
        }catch (Exception e){
            return r.get(-1);
        }
    }


    @PostMapping("/api/document/like")
    public Map<String, Object> collect(@RequestBody Map<String, String> req){
        String docId = req.get("docId");
        String userId = req.get("userId");
        Response res = new Response();
        try{
            documentService.collect(userId, docId);
            return res.get(0);
        }catch (NoAuth e){
            return res.get(2);
        }catch (AlreadyCollect e){
            return res.get(1);
        }catch (OperationFail e){
            return res.get(-1);
        }
    }
    @PostMapping("/api/document/dislike")
    public Map<String, Object> disCollect(@RequestBody Map<String, String> req){
        String docId = req.get("docId");
        String userId = req.get("userId");
        Response res = new Response();
        try{
            documentService.disCollect(userId, docId);
            return res.get(0);
        }catch (NotyetCollect e){
            return res.get(1);
        }catch (OperationFail e){
            return res.get(-1);
        }
    }
    @GetMapping("/api/document/share-check")
    public Map<String, Object> checkShare(@RequestParam String docId){
        Response r = new Response("auth");
        try{
            return r.get(0, documentService.checkShare(docId));
        }catch (Exception e){
            return r.get(-1);
        }
    }
    @PostMapping("/api/document/share")
    public Map<String, Object> share(@RequestBody Map<String, String> req){
        String docId = req.get("docId");
        String auth = req.get("auth");
        Response res = new Response();
        documentService.share(docId, auth);
        return res.get(0);
    }

    @PostMapping("/api/document/img")
    public Map<String, Object> uploadImg(@RequestParam MultipartFile file){
        try {
            return new Response("url").get(0, localFileService.saveDocumentImg(file));
        }catch (OperationFail e){
            return new Response("url").get(-1);
        }
    }
}
