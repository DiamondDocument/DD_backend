package diamondpick.dd_backend.Old.zzy.Controller;

import diamondpick.dd_backend.zzy.ServiceImp.FileServiceImp;

/*
@RestController
public class DocumentController {
    private DocumentService documentService = new DocumentImp();
    private FileService fileService = new FileServiceImp();
    static class CollectReq{
        String userId;
        String docId;
    }
    @PostMapping("/api/document/like")
    public HashMap<String,Object> collectDoc(@RequestBody CollectReq req){
        Response res = new Response();
        try{
            documentService.collect(req.userId, req.docId);
            return res.get(0);
        }catch (Exception e){
            return res.get(-1);
        }


    }
    @PostMapping("/api/document/dislike")
    public HashMap<String,Object> discollectDoc(@RequestBody CollectReq req){
        Response res = new Response();
        try{
            documentService.discollect(req.userId, req.docId);
            return res.get(0);
        }catch (Exception e){
            return res.get(-1);
        }
    }
    @PostMapping(value = "/api/document/save")
    public HashMap<String,Object> saveDoc(@RequestParam String content, @RequestParam String userId, @RequestParam String docId){
        Response res = new Response();
        try{
            //检查权限
            if(documentService.checkAuth(docId, userId) < 4){
                return res.get(2);
            }
            fileService.saveDocument(docId, content);
            return new Response().get(0);
        }catch (UserNotExist e){
            return res.get(2);
        }
        catch (DocNotExist e){
            return res.get(1);
        }
        catch(Exception e){
            return res.get(-1);
        }
    }
    @GetMapping(value="/api/document")
    public HashMap<String,Object> getDoc(@RequestParam String userId, @RequestParam String docId) {
        Response res = new Response("content");
        try{
            if(documentService.checkAuth(docId, userId) < 2){
                return res.get(2, "没有权限");
            }
            return new Response().get(0, fileService.getDocument(docId));
        }catch (UserNotExist e){
            return res.get(2, "用户不存在");
        }
        catch (DocNotExist e){
            return res.get(1, "文档不存在");
        }
        catch(Exception e){
            return res.get(-1, null);
        }
    }
    @PostMapping(value = "/api/document/img")
    public HashMap<String,Object> postImage(@RequestParam MultipartFile file){
        Response res = new Response("url");
        try{
            return res.get(0, fileService.saveDocumentImg(file));
        }catch(Exception e){
            return res.get(-1, "上传错误");
        }
    }
    @GetMapping(value="/api/document/img/{filename}")
    public @ResponseBody void getImage(@PathVariable String filename, HttpServletResponse response) throws IOException {
        try{
            response.reset();
            response.setContentType(fileService.getImageContentType(filename));
            response.getOutputStream().write(fileService.getImage(filename));
            return;
        }catch (Exception e){
        }
    }

    //    static class CreateDocumentReq{
//        String name;
//        String userId;
//        int authority;
//        String fatherId;
//    }
//    @PostMapping(value = "/api/document/create")
//    public HashMap<String,Object> CreateDocument(@RequestBody CreateDocumentReq req){
////        Document newDoc;
////        if((newDoc = documentService.newDoc(req.name, req.userId, req.authority, req.fatherId)) == null){
////            return response(-1,"documentId",null);
////        }
////        return  response(0, "documentId", newDoc.getId());
//        return null;
//    }
}


 */

