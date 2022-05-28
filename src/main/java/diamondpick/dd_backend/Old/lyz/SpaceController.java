package diamondpick.dd_backend.Old.lyz;

/*
@RestController
public class SpaceController {
    @Autowired
    private SpaceService spaceService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;

    @Autowired
    private FolderService folderService;

    @GetMapping("/api/space/my")
    public Map<String, Object> mySpace(@RequestParam("userId") String userId, @RequestParam("folderId") String folderId) {
        Map<String, Object> map = new HashMap<>();
        try {
            UserSpace userSpace = spaceService.getUserSpace(userId);
            String userspaceId = userSpace.getSpaceId();
            String userspaceName = userSpace.getSpaceName();
            map.put("code", 0);
            map.put("userspaceId", userspaceId);
            map.put("userspaceName", userspaceName);

            ArrayList<JSONObject> jsonList = new ArrayList<>();
            ArrayList<Document> documents = documentService.getDocumentBySpaceId(userspaceId);
            for (Document document : documents) {
                JSONObject json = new JSONObject();
                json.put("fileId", document.getId());
                json.put("fileName", document.getName());
                json.put("creatorId", document.getCreatorId());
                json.put("creatorName", userService.selectUserByUserId(document.getCreatorId()).getNickname());
                json.put("createTime", document.getCreateTime());
                json.put("modifierId", document.getModifierId());
                json.put("modifierName", userService.selectUserByUserId(document.getModifierId()).getNickname());
                json.put("modifyTime", document.getModifyTime());
                json.put("size", documentService.getSize(document.getId()));
                jsonList.add(json);
            }

            ArrayList<Folder> folders = folderService.getFolderBySpaceId(userspaceId);
            for (Folder folder : folders) {
                JSONObject json = new JSONObject();
                json.put("folderId", folder.getFolderId());
                json.put("folderName", folder.getFolderName());
                json.put("creatorId", folder.getCreatorId());
                json.put("creatorName", userService.selectUserByUserId(folder.getCreatorId()).getNickname());
                json.put("createTime", folder.getCreateTime());
                if (folder.getFolderId().equals(folderId)) {
                    List<JSONObject> jsonListInFolder = new ArrayList<>();
                    List<Document> documentInFolder = documentService.getDocumentByParentId(folderId);
                    for (Document document : documentInFolder) {
                        JSONObject jsonInFolder = new JSONObject();
                        jsonInFolder.put("fileId", document.getId());
                        jsonInFolder.put("fileName", document.getName());
                        jsonInFolder.put("creatorId", document.getCreatorId());
                        jsonInFolder.put("creatorName", userService.selectUserByUserId(document.getCreatorId()).getNickname());
                        jsonInFolder.put("createTime", document.getCreateTime());
                        jsonInFolder.put("modifierId", document.getModifierId());
                        jsonInFolder.put("modifierName", userService.selectUserByUserId(document.getModifierId()).getNickname());
                        jsonInFolder.put("modifyTime", document.getModifyTime());
                        jsonInFolder.put("size", documentService.getSize(document.getId()));
                        jsonListInFolder.add(jsonInFolder);
                    }
                    ArrayList<Folder> folderInFolder = folderService.getFolderByParentId(folderId);
                    for (Folder folder1 : folderInFolder) {
                        JSONObject jsonInFolder = new JSONObject();
                        jsonInFolder.put("folderId", folder1.getFolderId());
                        jsonInFolder.put("folderName", folder1.getFolderName());
                        jsonInFolder.put("creatorId", folder1.getCreatorId());
                        jsonInFolder.put("creatorName", userService.selectUserByUserId(folder1.getCreatorId()).getNickname());
                        jsonInFolder.put("createTime", folder1.getCreateTime());
                        jsonListInFolder.add(jsonInFolder);
                    }
                    json.put("files", jsonListInFolder);
                }
                jsonList.add(json);
            }

            map.put("files", jsonList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("message", "其他错误");
        }
        return map;
    }

    @PostMapping("/api/space/team")
    public Map<String, Object> teamSpace(@RequestBody Map<String, String> requestMap) {
        String teamId = requestMap.get("teamId");
        Map<String, Object> map = new HashMap<>();
//        try {
//            ArrayList<Space> spaces = spaceService.getTeamSpace(teamId);
//            if (spaces.size() == 0) {
//                map.put("error", 0);
//                map.put("message", "团队空间为空");
//            } else {
//                ArrayList<JSONObject> jsonList = new ArrayList<>();
//                JSONObject jsonObject = new JSONObject();
//                for (Space space : spaces) {
//                    jsonObject.put("spaceId", space.getSpaceId());
//                    jsonObject.put("spaceName", space.getSpaceName());
//                    jsonObject.put("creatorId", space.getCreatorId());
//                    jsonObject.put("createTime", space.getCreateTime());
//                }
//                map.put("error", 0);
//                map.put("teamSpace", jsonList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("error", -1);
//            map.put("message", "其他错误");
//        }
        return map;
    }

    @PostMapping("/api/space/collection")
    public Map<String, Object> collectionSpace(@RequestBody Map<String, String> requestMap) {
        String userId = requestMap.get("userId");
        Map<String, Object> map = new HashMap<>();
//        try {
//            ArrayList<Space> spaces = spaceService.getCollectionSpace(userId);
//            if (spaces.size() == 0) {
//                map.put("error", 0);
//                map.put("message", "收藏空间为空");
//            } else {
//                ArrayList<JSONObject> jsonList = new ArrayList<>();
//                JSONObject jsonObject = new JSONObject();
//                for (Space space : spaces) {
//                    jsonObject.put("spaceId", space.getSpaceId());
//                    jsonObject.put("spaceName", space.getSpaceName());
//                    jsonObject.put("creatorId", space.getCreatorId());
//                    jsonObject.put("createTime", space.getCreateTime());
//                }
//                map.put("error", 0);
//                map.put("collectionSpace", jsonList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("error", -1);
//            map.put("message", "其他错误");
//        }
        return map;
    }

    @PostMapping("/api/space/recycle")
    public Map<String, Object> recycleSpace(@RequestBody Map<String, String> requestMap) {
        String userId = requestMap.get("userId");
        Map<String, Object> map = new HashMap<>();
//        try {
//            ArrayList<Space> spaces = spaceService.getRecycleSpace(userId);
//            if (spaces.size() == 0) {
//                map.put("error", 0);
//                map.put("message", "回收站为空");
//            } else {
//                ArrayList<JSONObject> jsonList = new ArrayList<>();
//                JSONObject jsonObject = new JSONObject();
//                for (Space space : spaces) {
//                    jsonObject.put("spaceId", space.getSpaceId());
//                    jsonObject.put("spaceName", space.getSpaceName());
//                    jsonObject.put("creatorId", space.getCreatorId());
//                    jsonObject.put("createTime", space.getCreateTime());
//                }
//                map.put("error", 0);
//                map.put("recycleSpace", jsonList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("error", -1);
//            map.put("message", "其他错误");
//        }
        return map;
    }

    @PostMapping("/api/space/last")
    public Map<String, Object> lastSpace(@RequestBody Map<String, String> requestMap) {
        String userId = requestMap.get("userId");
        Map<String, Object> map = new HashMap<>();
//        try {
//            ArrayList<Space> spaces = spaceService.getLastSpace(userId);
//            if (spaces.size() == 0) {
//                map.put("error", 0);
//                map.put("message", "最近访问为空");
//            } else {
//                ArrayList<JSONObject> jsonList = new ArrayList<>();
//                JSONObject jsonObject = new JSONObject();
//                for (Space space : spaces) {
//                    jsonObject.put("spaceId", space.getSpaceId());
//                    jsonObject.put("spaceName", space.getSpaceName());
//                    jsonObject.put("creatorId", space.getCreatorId());
//                    jsonObject.put("createTime", space.getCreateTime());
//                }
//                map.put("error", 0);
//                map.put("lastSpace", jsonList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("error", -1);
//            map.put("message", "其他错误");
//        }
        return map;
    }

    @PostMapping("/api/space/authority")
    public Map<String, Object> changeAuthority(@RequestBody Map<String, String> requestMap) {
        Map<String, Object> map = new HashMap<>();

        return map;
    }
}


 */