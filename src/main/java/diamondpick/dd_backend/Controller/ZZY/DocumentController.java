package diamondpick.dd_backend.Controller.ZZY;

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

    public HashMap<String,Object> response(int code, String... key){
        HashMap<String,Object> map = new HashMap<>();
        map.put("code", code);
        for(int i = 0; i < key.length; i+=2){
            map.put((String)key[i], key[i+1]);
        }
        return map;
    }


    static class CreateDocumentReq{
        String name;
        String userId;
        int authority;
        String fatherId;
    }
    @PostMapping(value = "/api/document/create")
    public HashMap<String,Object> CreateDocument(@RequestBody CreateDocumentReq req){
        Document newDoc;
        if((newDoc = documentService.newDoc(req.name, req.userId, req.authority, req.fatherId)) == null){
            return response(-1,"documentId",null);
        }
        return  response(0, "documentId", newDoc.getId());
    }
}
class Response{
    public Response(int code, String... key){

        for(int i = 0; i < key.length; i++){
            map.put(key[i],null);
        }
    }

    private HashMap<String,Object> map;
    public HashMap<String,Object> get(){
        return map;
    }
}
