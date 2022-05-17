package diamondpick.dd_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import diamondpick.dd_backend.Entity.Space;
import diamondpick.dd_backend.Service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SpaceController {
    @Autowired
    private SpaceService spaceService;

    @PostMapping("/api/space/my")
    public Map<String, Object> mySpace(@RequestBody Map<String, String> requestMap) {
        String userId = requestMap.get("userId");
        Map<String, Object> map = new HashMap<>();
        try {
            ArrayList<Space> spaces = spaceService.getMySpace(userId);
            if (spaces.size() == 0) {
                map.put("error", 0);
                map.put("message", "我的空间为空");
            } else {
                ArrayList<JSONObject> jsonList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();
                for (Space space : spaces) {
                    jsonObject.put("spaceId", space.getSpaceId());
                    jsonObject.put("spaceName", space.getSpaceName());
                    jsonObject.put("creatorId", space.getCreatorId());
                    jsonObject.put("createTime", space.getCreateTime());
                }
                map.put("error", 0);
                map.put("mySpace", jsonList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", -1);
            map.put("message", "其他错误");
        }
        return map;
    }

    @PostMapping("/api/space/team")
    public Map<String, Object> teamSpace(@RequestBody Map<String, String> requestMap) {
        String teamId = requestMap.get("teamId");
        Map<String, Object> map = new HashMap<>();
        try {
            ArrayList<Space> spaces = spaceService.getTeamSpace(teamId);
            if (spaces.size() == 0) {
                map.put("error", 0);
                map.put("message", "团队空间为空");
            } else {
                ArrayList<JSONObject> jsonList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();
                for (Space space : spaces) {
                    jsonObject.put("spaceId", space.getSpaceId());
                    jsonObject.put("spaceName", space.getSpaceName());
                    jsonObject.put("creatorId", space.getCreatorId());
                    jsonObject.put("createTime", space.getCreateTime());
                }
                map.put("error", 0);
                map.put("teamSpace", jsonList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", -1);
            map.put("message", "其他错误");
        }
        return map;
    }

    @PostMapping("/api/space/collection")
    public Map<String, Object> collectionSpace(@RequestBody Map<String, String> requestMap) {
        String userId = requestMap.get("userId");
        Map<String, Object> map = new HashMap<>();
        try {
            ArrayList<Space> spaces = spaceService.getCollectionSpace(userId);
            if (spaces.size() == 0) {
                map.put("error", 0);
                map.put("message", "收藏空间为空");
            } else {
                ArrayList<JSONObject> jsonList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();
                for (Space space : spaces) {
                    jsonObject.put("spaceId", space.getSpaceId());
                    jsonObject.put("spaceName", space.getSpaceName());
                    jsonObject.put("creatorId", space.getCreatorId());
                    jsonObject.put("createTime", space.getCreateTime());
                }
                map.put("error", 0);
                map.put("collectionSpace", jsonList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", -1);
            map.put("message", "其他错误");
        }
        return map;
    }

    @PostMapping("/api/space/recycle")
    public Map<String, Object> recycleSpace(@RequestBody Map<String, String> requestMap) {
        String userId = requestMap.get("userId");
        Map<String, Object> map = new HashMap<>();
        try {
            ArrayList<Space> spaces = spaceService.getRecycleSpace(userId);
            if (spaces.size() == 0) {
                map.put("error", 0);
                map.put("message", "回收站为空");
            } else {
                ArrayList<JSONObject> jsonList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();
                for (Space space : spaces) {
                    jsonObject.put("spaceId", space.getSpaceId());
                    jsonObject.put("spaceName", space.getSpaceName());
                    jsonObject.put("creatorId", space.getCreatorId());
                    jsonObject.put("createTime", space.getCreateTime());
                }
                map.put("error", 0);
                map.put("recycleSpace", jsonList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", -1);
            map.put("message", "其他错误");
        }
        return map;
    }

    @PostMapping("/api/space/last")
    public Map<String, Object> lastSpace(@RequestBody Map<String, String> requestMap) {
        String userId = requestMap.get("userId");
        Map<String, Object> map = new HashMap<>();
        try {
            ArrayList<Space> spaces = spaceService.getLastSpace(userId);
            if (spaces.size() == 0) {
                map.put("error", 0);
                map.put("message", "最近访问为空");
            } else {
                ArrayList<JSONObject> jsonList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();
                for (Space space : spaces) {
                    jsonObject.put("spaceId", space.getSpaceId());
                    jsonObject.put("spaceName", space.getSpaceName());
                    jsonObject.put("creatorId", space.getCreatorId());
                    jsonObject.put("createTime", space.getCreateTime());
                }
                map.put("error", 0);
                map.put("lastSpace", jsonList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", -1);
            map.put("message", "其他错误");
        }
        return map;
    }

    @PostMapping("/api/space/authority")
    public Map<String, Object> changeAuthority (@RequestBody Map<String, String> requestMap) {
        Map<String, Object> map = new HashMap<>();

        return map;
    }
}
