package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.FolderDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.FileService;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class FileController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private FolderDao folderDao;

    @Autowired
    private AuthService authService;

    @PostMapping("/api/file/create")
    public Map<String, Object> createFile(@RequestParam(value = "type") String type,
                                          @RequestParam(value = "name") String name,
                                          @RequestParam(value = "creatorId") String creatorId,
                                          @RequestParam(value = "authority", required = false) String authority,
                                          @RequestParam(value = "teamId", required = false) String teamId,
                                          @RequestParam(value = "parentId", required = false) String parentId,
                                          @RequestParam(value = "templateId", required = false) String templateId,
                                          @RequestParam(required = false) MultipartFile file) {
        Response res = new Response("fileId");
        int auth = 3;
        int spaceId;
        if (authority != null) {
            auth = Integer.parseInt(authority);
        }
        if (teamId != null) {
            spaceId = teamDao.selectTeam(teamId).getSpaceId();
        } else {
            spaceId = userDao.selectUser(creatorId).getSpaceId();
        }
        try {
            String fileId;
            if (type.equals("1")) {
                // create document
                if (templateId == null) {
                    fileId = documentService.newDoc(name, String.valueOf(spaceId), creatorId, parentId, file);
                    documentDao.updateDoc(fileId, "self_auth", auth);
                    if (parentId != null) {
                        Folder parent = folderDao.selectFolder(parentId);
                        int nowAuth = Math.min(parent.getSelfAuth(), auth);
                        documentDao.updateDoc(fileId, "now_auth", nowAuth);
                    } else {
                        documentDao.updateDoc(fileId, "now_auth", auth);
                    }
                } else {
                    fileId = documentService.newDocByTemplate(name, String.valueOf(spaceId), creatorId, parentId, templateId);
                    documentDao.updateDoc(fileId, "self_auth", auth);
                    if (parentId != null) {
                        Folder parent = folderDao.selectFolder(parentId);
                        int nowAuth = Math.min(parent.getSelfAuth(), auth);
                        documentDao.updateDoc(fileId, "now_auth", nowAuth);
                    } else {
                        documentDao.updateDoc(fileId, "now_auth", auth);
                    }
                }
            } else if (type.equals("2")) {
                // create folder
                fileId = fileService.newFolder(name, creatorId, parentId, String.valueOf(spaceId));
                folderDao.updateFolder(fileId, "self_auth", auth);
                if (parentId != null) {
                    Folder parent = folderDao.selectFolder(parentId);
                    int nowAuth = Math.min(parent.getSelfAuth(), auth);
                    folderDao.updateFolder(fileId, "now_auth", nowAuth);
                } else {
                    folderDao.updateFolder(fileId, "now_auth", auth);
                }
            } else {
                return res.get(-1);
            }
            return res.get(0, fileId);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @PostMapping("/api/file/rename")
    public Map<String, Object> renameFile(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String fileId = re_map.get("fileId");
        String newName = re_map.get("newName");
        try {
            if (fileId.charAt(0) == 'f') {
                folderDao.updateFolder(fileId, "name", newName);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateDoc(fileId, "name", newName);
            } else {
                return res.get(-1);
            }
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @PostMapping("/api/file/move")
    public Map<String, Object> moveFile(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String fileId = re_map.get("fileId");
        String newParentId = re_map.get("newParentId");
        try {
            if (fileId.charAt(0) == 'f') {
                folderDao.updateFolder(fileId, "parent_id", newParentId);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateDoc(fileId, "parent_id", newParentId);
            } else {
                return res.get(-1);
            }
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @PostMapping("/api/file/remove")
    public Map<String, Object> removeFile(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String userId = re_map.get("userId");
        String fileId = re_map.get("fileId");
        try {

            if (fileId.charAt(0) == 'f') {
                folderDao.updateToDelete(fileId, userId);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateToDelete(fileId, userId);
            } else {
                return res.get(-1);
            }
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @PostMapping("/api/file/complete-remove")
    public Map<String, Object> completeRemoveFile(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String userId = re_map.get("userId");
        String fileId = re_map.get("fileId");
        try {
            if (authService.checkFileAuth(fileId, userId) < 4) {
                return res.get(-1);
            }
            if (fileId.charAt(0) == 'f') {
                fileService.deletePermanently(fileId, userId);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.deleteDoc(fileId);
            } else {
                return res.get(-1);
            }
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @PostMapping("/api/file/recover")
    public Map<String, Object> recoverFile(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String fileId = re_map.get("fileId");
        try {
            if (fileId.charAt(0) == 'f') {
                folderDao.updateFolder(fileId, "is_delete", "0");
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateDoc(fileId, "is_delete", "0");
            } else {
                return res.get(-1);
            }
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @PostMapping("api/file/authority")
    public Map<String, Object> changeAuthority(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String fileId = re_map.get("fileId");
        int authority = Integer.parseInt(re_map.get("newAuth"));
        try {
            if (fileId.charAt(0) == 'f') {
                // change selfAuth
                folderDao.updateFolder(fileId, "self_auth", authority);
                // change nowAuth
                String parentId = folderDao.selectFolder(fileId).getParentId();
                if (parentId != null) {
                    Folder parent = folderDao.selectFolder(parentId);
                    authority = Math.min(parent.getNowAuth(), authority);
                }
                folderDao.updateFolder(fileId, "now_auth", authority);
                fileService.updateAuthRecur(fileId, authority);
            } else if (fileId.charAt(0) == 'd') {
                // change selfAuth
                documentDao.updateDoc(fileId, "self_auth", authority);
                // change nowAuth
                String parentId = documentDao.selectDoc(fileId).getParentId();
                if (parentId != null) {
                    Folder parent = folderDao.selectFolder(parentId);
                    int nowAuth = Math.min(parent.getNowAuth(), authority);
                    documentDao.updateDoc(fileId, "now_auth", nowAuth);
                } else {
                    documentDao.updateDoc(fileId, "now_auth", authority);
                }
            } else {
                return res.get(-1);
            }
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }
}
