package diamondpick.dd_backend.Controller;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.FolderDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.FileService;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/api/file/create")
    public Map<String, Object> createFile(@RequestBody Map<String, String> re_map) {
        Response res = new Response("fileId");
        int type = Integer.parseInt(re_map.get("type"));
        String name = re_map.get("name");
        String creatorId = re_map.get("creatorId");
        int authority = 3;
        int spaceId;
        String parentId = null;
        if (re_map.get("authority") != null) {
            authority = Integer.parseInt(re_map.get("authority"));
        }
        if (re_map.get("teamId") != null) {
            spaceId = teamDao.selectTeam(re_map.get("teamId")).getSpaceId();
        } else {
            spaceId = userDao.selectUser(creatorId).getSpaceId();
        }
        if (re_map.get("parentId") != null) {
            parentId = re_map.get("parentId");
        }
        try {
            String fileId;
            if (type == 1) {
                // create document
                if (re_map.get("templateId") == null) {
                    fileId = documentService.newDoc(name, String.valueOf(spaceId), creatorId, parentId);
                    documentDao.updateDoc(fileId, "self_auth", authority);
                } else {
                    fileId = documentService.newDocByTemplate(name, String.valueOf(spaceId), creatorId, parentId, re_map.get("templateId"));
                    documentDao.updateDoc(fileId, "self_auth", authority);
                }
            } else if (type == 2) {
                // create folder
                fileId = fileService.newFolder(name, creatorId, parentId, String.valueOf(spaceId));
                folderDao.updateFolder(fileId, "self_auth", authority);
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
                folderDao.updateFolder(fileId, "self_auth", authority);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateDoc(fileId, "self_auth", authority);
            } else {
                return res.get(-1);
            }
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }
}
