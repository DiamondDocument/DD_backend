package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.TemplateDao;
import diamondpick.dd_backend.Entity.Template;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.LocalFileService;
import diamondpick.dd_backend.Service.TemplateService;
import diamondpick.dd_backend.Tool.JsonArray;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TemplateController {
    @Autowired
    TemplateService templateService;
    @Autowired
    TemplateDao templateDao;
    @Autowired
    LocalFileService localFileService;

    @PostMapping("/api/template/create")
    public Map<String, Object> create(@RequestBody Map<String, String> req){
        Response r = new Response("tempId");
        String docId = req.get("docId");
        String name = req.get("name");
        String intro = req.get("intro");
        try{
            return r.get(0, templateService.newTemp(docId, name, intro));
        }catch (OperationFail e){
            return r.get(-1);
        }
    }
    @GetMapping(value="/api/template/list/my")
    public Map<String, Object> my(@RequestParam String userId) {
        Response res = new Response("temps");
        JsonArray arr = new JsonArray("tempId", "tempName", "creatorId", "creatorName", "createTime", "url");
        try{
            List<Template> temps =  templateService.getMyTemplate(userId);
            for(Template temp : temps){
                arr.add(temp.getTempId(), temp.getName(), temp.getCreatorId(), temp.getCreatorName(), temp.getCreateTime(), localFileService.getThumbnailUrl(temp.getTempId()));
            }
            return res.get(0, arr);
        }catch (Exception e){
            return res.get(-1);
        }
    }
    @GetMapping(value="/api/template/list/collection")
    public Map<String, Object> collection(@RequestParam String userId) {
        Response res = new Response("temps");
        JsonArray arr = new JsonArray("tempId", "tempName", "creatorId", "creatorName", "createTime", "url");
        try{
            List<Template> temps =  templateService.getCollection(userId);
            for(Template temp : temps){
                arr.add(temp.getTempId(), temp.getName(), temp.getCreatorId(), temp.getCreatorName(), temp.getCreateTime(), localFileService.getThumbnailUrl(temp.getTempId()));
            }
            return res.get(0, arr);
        }catch (Exception e){
            return res.get(-1);
        }
    }
    @GetMapping(value="/api/template/list/recommend")
    public Map<String, Object> recommend(@RequestParam String userId) {
        Response res = new Response("temps");
        JsonArray arr = new JsonArray("tempId", "tempName", "creatorId", "creatorName", "createTime", "url");
        try{
            List<Template> temps =  templateService.getRecommend(userId);
            for(Template temp : temps){
                arr.add(temp.getTempId(), temp.getName(), temp.getCreatorId(), temp.getCreatorName(), temp.getCreateTime(), localFileService.getThumbnailUrl(temp.getTempId()));
            }
            return res.get(0, arr);
        }catch (Exception e){
            return res.get(-1);
        }
    }

    @PostMapping("/api/template/like")
    public Map<String, Object> like(@RequestBody Map<String, String> req){
        Response r = new Response();
        String userId = req.get("userId");
        String tempId = req.get("tempId");
        try{
            templateService.collect(userId, tempId);
            return r.get(0);
        }catch (AlreadyCollect e){
            return r.get(1);
        }catch (OperationFail e){
            return r.get(-1);
        }
    }
    @PostMapping("/api/template/dislike")
    public Map<String, Object> dislike(@RequestBody Map<String, String> req){
        Response r = new Response();
        String userId = req.get("userId");
        String tempId = req.get("tempId");
        try{
            templateService.disCollect(userId, tempId);
            return r.get(0);
        }catch (NotyetCollect e){
            return r.get(1);
        }catch (OperationFail e){
            return r.get(-1);
        }
    }
    @GetMapping(value="/api/template")
    public Map<String, Object> information(@RequestParam String tempId) {
        Response res = new Response("name", "intro", "creatorId", "creatorName");
        try{
            Template t = templateDao.selectTemp(tempId);
            return res.get(0, t.getName(), t.getIntro(), t.getCreatorId(), t.getCreatorName());
        }catch (Exception e){
            return res.get(-1);
        }
    }
    @GetMapping("/api/template/content")
    public Map<String, Object> getContent(@RequestParam String tempId){
        Response res = new Response("content");
        try{
            return res.get(0, localFileService.getTemplate(tempId));
        }catch (OperationFail e){
            return res.get(-1);
        }
    }

    @GetMapping("/api/template/thumbnail")
    public Map<String, Object> getThumbnail(@RequestParam String tempId){
        Response res = new Response("url");
        try{
            return res.get(0, localFileService.getThumbnailUrl(tempId));
        }catch (OperationFail e){
            return res.get(-1);
        }
    }
    @GetMapping("/api/template/image")
    public Map<String, Object> getImage(@RequestParam String tempId){
        Response res = new Response("url");
        try{
            return res.get(0, localFileService.getTemplateImageUrl(tempId));
        }catch (OperationFail e){
            return res.get(-1);
        }
    }

}
