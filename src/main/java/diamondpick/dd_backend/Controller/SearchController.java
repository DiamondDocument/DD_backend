package diamondpick.dd_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import diamondpick.dd_backend.Dao.SearchDao;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.LocalFileService;
import diamondpick.dd_backend.Tool.JsonArray;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    SearchDao searchDao;
    @Autowired
    LocalFileService localFileService;
    @Autowired
    AuthService authService;
    @Autowired
    DocumentService documentService;

    @GetMapping("/api/search/team")
    public Map<String, Object> searchTeam(@RequestParam String key){
        Response r = new Response("teams");
        JsonArray arr = new JsonArray("name", "intro", "teamId", "url");
        try{
            List<Team> teams = searchDao.searchTeam(key);
            for(Team team: teams){
                arr.add(team.getName(), team.getIntro(), team.getTeamId(), localFileService.getTeamAvatarUrl(team.getTeamId()));
            }
            return r.get(0, arr);
        }catch (Exception e){
            e.printStackTrace();
            return r.get(-1);
        }
    }

    @GetMapping("/api/search/user")
    public Map<String, Object> searchUser(@RequestParam String key){
        Response r = new Response("users");
        JsonArray arr = new JsonArray("nickName", "intro", "userId", "url");
        try{
            List<User> users = searchDao.searchUser(key);
            for(User user: users){
                arr.add(user.getNickname(), user.getIntro(), user.getUserId(), localFileService.getUserAvatarUrl(user.getUserId()));
            }
            return r.get(0, arr);
        }catch (Exception e){
            e.printStackTrace();
            return r.get(-1);
        }
    }

    @GetMapping("/api/search/document")
    public Map<String, Object> searchDoc(@RequestParam String type, @RequestParam String ownerId,
                                         @RequestParam String visitorId, @RequestParam String key){
        Response r = new Response("documents");
        JsonArray arr = new JsonArray("fileId", "fileName", "creatorId", "creatorName", "createTime",
                "modifierId", "modifierName", "modifyTime", "size", "fileType");
        try {
                List<Document> documents = searchDao.searchDoc(type, ownerId, key);
                for (Document document : documents) {
                    if (authService.checkFileAuth(document.getFileId(), visitorId) >= 2) {
                        arr.add(document.getFileId(), document.getName(), document.getCreatorId(),
                                document.getCreatorName(), document.getCreateTime(), document.getModifierId(),
                                document.getModifierName(), document.getModifyTime(), documentService.getSize(document.getFileId()), 1);
                }
            }
            return r.get(0, arr);
        } catch (Exception e) {
            e.printStackTrace();
            return r.get(-1);
        }
    }
}
