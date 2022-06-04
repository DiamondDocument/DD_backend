package diamondpick.dd_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import diamondpick.dd_backend.Dao.*;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SpaceController {
    @Autowired
    private FolderDao folderDao;

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private SpaceDao spaceDao;

    @Autowired
    private AuthService authService;

    @GetMapping("/api/space")
    public Map<String, Object> getSpace(@RequestParam(value = "type") String type,
                                        @RequestParam(value = "ownerId") String ownerId,
                                        @RequestParam(value = "folderId", required = false) String folderId,
                                        @RequestParam(value = "visitorId") String visitorId) {
        Response res = new Response("files");
        try {
            List<JSONObject> jsonList = new ArrayList<>();
            if (folderId == null) {
                List<Folder> folders = folderDao.selectRootDir(type, ownerId);
                List<Document> documents = documentDao.selectRootDir(type, ownerId);
                folders.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                documents.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                for (Folder folder : folders) {
                    if (authService.checkFileAuth(folder.getFileId(), visitorId) >= 2) {
                        JSONObject json = new JSONObject();
                        json.put("fileType", 2);
                        json.put("fileId", folder.getFileId());
                        json.put("fileName", folder.getName());
                        json.put("creatorId", folder.getCreatorId());
                        json.put("creatorName", folder.getCreatorName());
                        json.put("createTime", folder.getCreateTime());
                        jsonList.add(json);
                    }
                }
                for (Document document : documents) {
                    if (authService.checkFileAuth(document.getFileId(), visitorId) >= 2) {
                        JSONObject json = new JSONObject();
                        json.put("fileType", 1);
                        json.put("fileId", document.getFileId());
                        json.put("fileName", document.getName());
                        json.put("creatorId", document.getCreatorId());
                        json.put("creatorName", document.getCreatorName());
                        json.put("createTime", document.getCreateTime());
                        json.put("modifierId", document.getModifierId());
                        json.put("modifierName", document.getModifierName());
                        json.put("modifyTime", document.getModifyTime());
                        json.put("size", document.getSize());
                        jsonList.add(json);
                    }
                }
            } else {
                List<Folder> folders = folderDao.selectSubDir(folderId);
                List<Document> documents = documentDao.selectSubDir(folderId);
                folders.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                documents.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                int spaceId;
                if (type.equals("user")) {
                    spaceId = userDao.selectUser(ownerId).getSpaceId();
                } else if (type.equals("team")) {
                    spaceId = teamDao.selectTeam(ownerId).getSpaceId();
                } else {
                    throw new Exception();
                }
                for (Folder folder : folders) {
                    if (authService.checkFileAuth(folder.getFileId(), visitorId) >= 2 &&
                            folder.getSpaceId().equals(String.valueOf(spaceId))) {
                        JSONObject json = new JSONObject();
                        json.put("fileType", 2);
                        json.put("fileId", folder.getFileId());
                        json.put("fileName", folder.getName());
                        json.put("creatorId", folder.getCreatorId());
                        json.put("creatorName", folder.getCreatorName());
                        json.put("createTime", folder.getCreateTime());
                        jsonList.add(json);
                    }
                }
                for (Document document : documents) {
                    if (authService.checkFileAuth(document.getFileId(), visitorId) >= 2 &&
                            document.getSpaceId().equals(String.valueOf(spaceId))) {
                        JSONObject json = new JSONObject();
                        json.put("fileType", 1);
                        json.put("fileId", document.getFileId());
                        json.put("fileName", document.getName());
                        json.put("creatorId", document.getCreatorId());
                        json.put("creatorName", document.getCreatorName());
                        json.put("createTime", document.getCreateTime());
                        json.put("modifierId", document.getModifierId());
                        json.put("modifierName", document.getModifierName());
                        json.put("modifyTime", document.getModifyTime());
                        json.put("size", document.getSize());
                        jsonList.add(json);
                    }
                }
            }
            return res.get(0, jsonList);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @PostMapping("/api/space/authority")
    public Map<String, Object> changeAuthority(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String type = re_map.get("type");
        String spaceOwnerId = re_map.get("spaceOwnerId");
        String newAuth = re_map.get("newAuth");
        try {
            if (Integer.parseInt(newAuth) < 1 || Integer.parseInt(newAuth) > 4) {
                throw new Exception();
            }
            spaceDao.updateAuth(type, spaceOwnerId, Integer.parseInt(newAuth));
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @GetMapping("/api/space/collection")
    public Map<String, Object> getCollection(@RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "visitorId") String visitorId) {
        Response res = new Response("files");
        try {
            List<JSONObject> jsonList = new ArrayList<>();
            List<Document> documents = documentDao.selectCollection(userId);
            documents.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
            for (Document document : documents) {
                JSONObject json = new JSONObject();
                if (authService.checkFileAuth(document.getFileId(), visitorId) >= 2) {
                    json.put("fileId", document.getFileId());
                    json.put("fileName", document.getName());
                    json.put("creatorId", document.getCreatorId());
                    json.put("creatorName", document.getCreatorName());
                    json.put("createTime", document.getCreateTime());
                    json.put("modifierId", document.getModifierId());
                    json.put("modifierName", document.getModifierName());
                    json.put("modifyTime", document.getModifyTime());
                    json.put("size", document.getSize());
                    jsonList.add(json);
                }
            }
            return res.get(0, jsonList);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @GetMapping("/api/space/recycle")
    public Map<String, Object> getRecycle(@RequestParam(value = "type") String type,
                                          @RequestParam(value = "ownerId") String ownerId) {
        Response res = new Response("files");
        try {
            List<JSONObject> jsonList = new ArrayList<>();
            List<Folder> folders = folderDao.selectDeleted(type, ownerId);
            List<Document> documents = documentDao.selectDeleted(type, ownerId);
            folders.sort((o1, o2) -> o2.getDeleteTime().compareTo(o1.getDeleteTime()));
            documents.sort((o1, o2) -> o2.getDeleteTime().compareTo(o1.getDeleteTime()));
            for (Folder folder : folders) {
                JSONObject json = new JSONObject();
                json.put("fileType", 2);
                json.put("fileId", folder.getFileId());
                json.put("fileName", folder.getName());
                json.put("creatorId", folder.getCreatorId());
                json.put("creatorName", folder.getCreatorName());
                json.put("createTime", folder.getCreateTime());
                json.put("deleterId", folder.getDeleterId());
                json.put("deleterName", folder.getDeleterName());
                json.put("deleteTime", folder.getDeleteTime());
                jsonList.add(json);
            }
            for (Document document : documents) {
                JSONObject json = new JSONObject();
                json.put("fileType", 1);
                json.put("fileId", document.getFileId());
                json.put("fileName", document.getName());
                json.put("creatorId", document.getCreatorId());
                json.put("creatorName", document.getCreatorName());
                json.put("createTime", document.getCreateTime());
                json.put("deleterId", document.getDeleterId());
                json.put("deleterName", document.getDeleterName());
                json.put("deleteTime", document.getDeleteTime());
                json.put("size", document.getSize());
                jsonList.add(json);
            }
            return res.get(0, jsonList);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @GetMapping("/api/space/last")
    public Map<String, Object> getLast(@RequestParam(value = "userId") String userId) {
        Response res = new Response("files");
        try {
            List<JSONObject> jsonList = new ArrayList<>();
            List<Document> documents = documentDao.selectRecent(userId, 10);
            for (Document document : documents) {
                JSONObject json = new JSONObject();
                if (authService.checkFileAuth(document.getFileId(), userId) >= 2) {
                    json.put("fileId", document.getFileId());
                    json.put("fileName", document.getName());
                    json.put("creatorId", document.getCreatorId());
                    json.put("creatorName", document.getCreatorName());
                    json.put("createTime", document.getCreateTime());
                    json.put("modifierId", document.getModifierId());
                    json.put("modifierName", document.getModifierName());
                    json.put("modifyTime", document.getModifyTime());
                    json.put("browserTime", document.getBrowseTime());
                    json.put("size", document.getSize());
                    jsonList.add(json);
                }
            }
            return res.get(0, jsonList);
        } catch (Exception e) {
            return res.get(-1);
        }
    }
}
