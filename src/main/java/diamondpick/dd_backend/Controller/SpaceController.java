package diamondpick.dd_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import diamondpick.dd_backend.Dao.*;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

    @Autowired
    private DocumentService documentService;

    @GetMapping("/api/space")
    public Map<String, Object> getSpace(@RequestParam(value = "type") String type,
                                        @RequestParam(value = "ownerId") String ownerId,
                                        @RequestParam(value = "folderId", required = false) String folderId,
                                        @RequestParam(value = "visitorId") String visitorId,
                                        @RequestParam(value = "isBack") boolean isBack) {
        Response res = new Response();
        try {
            List<JSONObject> jsonList = new ArrayList<>();
            if (isBack && folderId == null) {
                throw new Exception("isBack is true, but folderId is null.");
            }

            if (isBack) {
                res = new Response("parentId", "files");
                String parentId = folderDao.selectFolder(folderId).getParentId();
                if (parentId == null) {
                    List<Folder> folders = folderDao.selectRootDir(type, ownerId);
                    List<Document> documents = documentDao.selectRootDir(type, ownerId);
                    folders.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                    documents.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));

                    addFolderToJsonList(jsonList, folders, visitorId, -1);
                    addDocumentToJsonList(jsonList, documents, visitorId, -1);
                } else {
                    List<Folder> folders = folderDao.selectSubDir(parentId);
                    List<Document> documents = documentDao.selectSubDir(parentId);
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

                    addFolderToJsonList(jsonList, folders, visitorId, spaceId);
                    addDocumentToJsonList(jsonList, documents, visitorId, spaceId);
                }
                return res.get(0, parentId, jsonList);
            } else {
                res = new Response("files");
                if (folderId == null) {
                    List<Folder> folders = folderDao.selectRootDir(type, ownerId);
                    List<Document> documents = documentDao.selectRootDir(type, ownerId);
                    folders.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                    documents.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));

                    addFolderToJsonList(jsonList, folders, visitorId, -1);
                    addDocumentToJsonList(jsonList, documents, visitorId, -1);
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

                    addFolderToJsonList(jsonList, folders, visitorId, spaceId);
                    addDocumentToJsonList(jsonList, documents, visitorId, spaceId);
                }
                return res.get(0, jsonList);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            int oldAuth = spaceDao.selectSpaceAuth(type, spaceOwnerId);
            spaceDao.updateAuth(type, spaceOwnerId, Math.min(Integer.parseInt(newAuth), oldAuth));
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

            addDocumentToJsonList(jsonList, documents, visitorId, -1);
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
                Folder parentFolder = folderDao.selectFolder(folder.getParentId());
                if (parentFolder != null && parentFolder.isDelete()) {
                    continue;
                }
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
                Folder parentFolder = folderDao.selectFolder(document.getParentId());
                if (parentFolder != null && parentFolder.isDelete()) {
                    continue;
                }
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
                json.put("size", documentService.getSize(document.getFileId()));
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

            addDocumentToJsonList(jsonList, documents, userId, -2);
            return res.get(0, jsonList);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    private void addFolderToJsonList(List<JSONObject> jsonList, List<Folder> folders, String visitorId, int spaceId) throws OperationFail {
        for (Folder folder : folders) {
            if (authService.checkFileAuth(folder.getFileId(), visitorId) >= 2 &&
                    (spaceId == -1 || folder.getSpaceId().equals(String.valueOf(spaceId)))) {
                JSONObject json = new JSONObject();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String creatorInfo = formatter.format(folder.getCreateTime()) + " by " + folder.getCreatorName();
                json.put("fileType", 2);
                json.put("fileId", folder.getFileId());
                json.put("fileName", folder.getName());
                json.put("creatorInfo", creatorInfo);
                json.put("creatorId", folder.getCreatorId());
                json.put("creatorName", folder.getCreatorName());
                json.put("createTime", folder.getCreateTime());
                json.put("authority", authService.checkFileAuth(folder.getFileId(), visitorId));
                jsonList.add(json);
            }
        }
    }

    private void addDocumentToJsonList(List<JSONObject> jsonList, List<Document> documents, String visitorId, int spaceId) throws OperationFail {
        for (Document document : documents) {
            if (authService.checkFileAuth(document.getFileId(), visitorId) >= 2 &&
                    (spaceId == -1 || spaceId == -2 || document.getSpaceId().equals(String.valueOf(spaceId)))) {
                JSONObject json = new JSONObject();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String creatorInfo = formatter.format(document.getCreateTime()) + " by " + document.getCreatorName();
                String modifyInfo;
                if (document.getModifyTime() == null) {
                    modifyInfo = "No modification";
                } else {
                    modifyInfo = formatter.format(document.getModifyTime()) + " by " + document.getModifierName();
                }
                json.put("fileType", 1);
                json.put("fileId", document.getFileId());
                json.put("fileName", document.getName());
                json.put("creatorInfo", creatorInfo);
                json.put("creatorId", document.getCreatorId());
                json.put("creatorName", document.getCreatorName());
                json.put("createTime", document.getCreateTime());
                json.put("modifyInfo", modifyInfo);
                json.put("modifierId", document.getModifierId());
                json.put("modifierName", document.getModifierName());
                json.put("modifyTime", document.getModifyTime());
                if (spaceId == -2) {
                    json.put("browserTime", document.getBrowseTime());
                }
                json.put("size", documentService.getSize(document.getFileId()));
                json.put("authority", authService.checkFileAuth(document.getFileId(), visitorId));
                int isShared = documentService.checkShare(document.getFileId());
                if (isShared == 1) {
                    json.put("isShared", 0);
                } else {
                    json.put("isShared", isShared);
                }
                jsonList.add(json);
            }
        }
    }
}
