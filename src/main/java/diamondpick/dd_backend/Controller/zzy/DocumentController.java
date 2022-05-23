package diamondpick.dd_backend.Controller.zzy;

import diamondpick.dd_backend.Entity.zzy.Document;
import diamondpick.dd_backend.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class DocumentController {
    @Autowired
    private DocumentService documentService;

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
}


