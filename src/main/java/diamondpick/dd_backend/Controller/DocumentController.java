package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.LocalFileService;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            if(authService.checkFileAuth(docId, userId) < 4)return res.get(2);
            if(documentDao.selectDoc(docId).isEditing()) return res.get(1);
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
    @GetMapping("/api/document/share-check")
    public Map<String, Object> checkShare(@RequestParam String docId){
        Response r = new Response("auth");
        try{
            return r.get(0, documentService.checkShare(docId));
        }catch (Exception e){
            return r.get(-1);
        }
    }

}
