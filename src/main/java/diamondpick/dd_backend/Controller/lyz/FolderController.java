package diamondpick.dd_backend.Controller.lyz;

import com.alibaba.fastjson.JSONObject;
import diamondpick.dd_backend.Entity.lyz.Folder;
import diamondpick.dd_backend.Service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FolderController {
    @Autowired
    private FolderService folderService;

    @PostMapping("/api/folder/create")
    public Map<String, Object> createFolder(@RequestBody Map<String, String> requestMap) {
        String creatorId = requestMap.get("userId");
        String folderName = requestMap.get("folderName");
        String parentFolderId = requestMap.get("parentFolderId");
        Map<String, Object> map = new HashMap<>();
        try {
            Folder newFolder = new Folder(folderName, creatorId, new Date(System.currentTimeMillis()), parentFolderId, 4, 4);
            folderService.createFolder(newFolder);
            map.put("error", 0);
            map.put("folderId", newFolder.getFolderId());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", -1);
            map.put("message", "其他错误");
        }
        return map;
    }

    @GetMapping("/api/folder/open")
    public Map<String, Object> openFolder(@RequestBody Map<String, String> requestMap) {
        String folderId = requestMap.get("folderId");
        Map<String, Object> map = new HashMap<>();
        try {
            ArrayList<Folder> folders;
            System.out.println("before folderService.openFolder");
            folders = folderService.openFolder(folderId);
            System.out.println("after folderService.openFolder");
            System.out.println("folders.size() = " + folders.size());
            // documents = documentService.getDocuments(folderId);
            map.put("error", 0);
            if (folders.size() == 0) {
                map.put("error", 0);
                map.put("message", "当前文件夹为空");
            } else {
                ArrayList<JSONObject> jsonList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();
                for (Folder folder : folders) {
                    jsonObject.put("folderId", folder.getFolderId());
                    jsonObject.put("folderName", folder.getFolderName());
                }
//                for (Document document : documents) {
//                    jsonObject.put("documentId", document.getDocumentId());
//                    jsonObject.put("documentName", document.getDocumentName());
//                }
                map.put("error", 0);
                map.put("folders", jsonList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", -1);
            map.put("message", "其他错误");
        }
        return map;
    }
}
