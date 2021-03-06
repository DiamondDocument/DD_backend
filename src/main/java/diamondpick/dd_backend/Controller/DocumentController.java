package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Entity.Comment;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.CannotEdit;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.Document.SomoneEditing;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.LocalFileService;
import diamondpick.dd_backend.Tool.JsonArray;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.MediaSize;
import java.nio.channels.NotYetConnectedException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class DocumentController {
    @Autowired
    DocumentDao documentDao;
    @Autowired
    DocumentService documentService;
    @Autowired
    AuthService authService;
    @Autowired
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
        Response res = new Response("content", "name", "authority", "modifyTime");
        try{
            if(authService.checkFileAuth(docId, userId) < 2)return res.get(1);
            Document doc = documentDao.selectDoc(docId);
            return res.get(0, localFileService.getDocument(docId), doc.getName(), authService.checkFileAuth(docId, userId), doc.getModifyTime());
        }catch (OperationFail e){
            return res.get(-1);
        }
    }
    @PostMapping("/api/document/save")
    public Map<String, Object> saveDoc(@RequestBody Map<String, String> req){
        String content = req.get("content");
        String docId = req.get("docId");
        String userId = req.get("userId");
        String name = req.get("newName");
        Response res = new Response();
        try{
            documentService.checkEdit(docId, userId);
            int auth = authService.checkFileAuth(docId, userId);
            if(content != null){
                if(auth < 4)return res.get(-1);
                localFileService.saveDocument(docId, content);
                documentDao.updateDoc(docId, "modify_time", new Date());
                documentDao.updateDoc(docId, "modifier_id", userId);
            }
            if(name != null){
                if(auth < 5)return res.get(-1);
                documentDao.updateDoc(docId, "name", name);
            }
            return res.get(0);
        }catch (Exception e){
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
        Response res = new Response();
        try{
            String docId = req.get("docId");
            int auth = Integer.parseInt(req.get("auth"));
            documentService.share(docId, auth);
            return res.get(0);
        }catch (Exception e){
            return res.get(-1);
        }
    }
    @PostMapping("/api/document/dis-share")
    public Map<String, Object> disShare(@RequestBody Map<String, String> req){
        String docId = req.get("docId");
        Response res = new Response();
        try{
            documentService.disShare(docId);
            return res.get(0);
        }catch (OperationFail e){
            return res.get(-1);
        }
    }

    @PostMapping("/api/document/img")
    public Map<String, Object> uploadImg(@RequestParam MultipartFile file){
        try {
            return new Response("url").get(0, localFileService.saveDocumentImg(file));
        }catch (OperationFail e){
            return new Response("url").get(-1);
        }
    }

    @PostMapping("/api/document/update-browse")
    public Map<String, Object> updateBrowse(@RequestBody Map<String, String> req){
        String docId = req.get("docId");
        String userId = req.get("userId");
        Response res = new Response();
        try{
            if(documentDao.selectRecentRecord(userId, docId) == null){
                documentDao.insertRecent(userId, docId);
            }else{
                documentDao.updateRecent(userId, docId);
            }
            return res.get(0);
        }catch (Exception e){
            e.printStackTrace();
            return res.get(-1);
        }
    }

    @GetMapping("/api/comment/list")
    public Map<String, Object> listComment(@RequestParam String docId){
        Response r = new Response("comments");
        JsonArray arr = new JsonArray("commentId", "content", "date", "userId", "userName", "url");
        try{
            List<Comment> comments = documentService.getComment(docId);
            for(Comment c : comments){
                arr.add(c.getCommentId(), c.getContent(), c.getCreateTime(), c.getCreatorId(), c.getCreatorName(), localFileService.getUserAvatarUrl(c.getCreatorId()));
            }
            return r.get(0, arr);
        }catch (OperationFail e){
            return r.get(-1);
        }
    }

    @PostMapping("/api/comment/add")
    public Map<String, Object> addComment(@RequestBody Map<String, String> req){
        String docId = req.get("docId");
        String userId = req.get("userId");
        String content = req.get("content");
        Response res = new Response();
        try{
            documentService.newComment(content, docId, userId);
            return res.get(0);
        }catch (NoAuth e){
            e.printStackTrace();
            return res.get(1);
        }catch (OperationFail e){
            e.printStackTrace();
            return res.get(-1);
        }
    }
    @PostMapping("/api/comment/remove")
    public Map<String, Object> removeComment(@RequestBody Map<String, Object> req){
        String userId = (String)req.get("userId");
        int commentId = (int)req.get("commentId");
        Response res = new Response();
        try{
            documentService.deleteComment(commentId, userId);
            return res.get(0);
        }catch (NoAuth e){
            return res.get(1);
        }catch (OperationFail e){
            e.printStackTrace();
            return res.get(-1);
        }
    }
}
