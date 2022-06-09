package diamondpick.dd_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import diamondpick.dd_backend.Dao.MessageDao;
import diamondpick.dd_backend.Entity.Message;
import diamondpick.dd_backend.Tool.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private MessageDao messageDao;

    @GetMapping("/api/message/list")
    public Map<String, Object> listMessage(@RequestParam(value = "userId") String userId, @RequestParam(value = "msgType", required = false) String msgType) {
        Response res = new Response("msg");
        try {
            List<Message> messages;
            List<JSONObject> jsonList = new ArrayList<>();
            if (msgType == null) {
                messages = messageDao.selectMsg(userId);
            } else {
                messages = messageDao.selectMsgByType(userId, Integer.parseInt(msgType));
            }
            for (Message message : messages) {
                JSONObject json = new JSONObject();
                json.put("msgId", message.getMsgId());
                json.put("msgType", message.getMsgType());
                json.put("senderName", message.getSenderName());
                json.put("senderId", message.getSenderId());
                json.put("sendTime", message.getSendTime());
                json.put("isRead", message.getMsgStatus());
                json.put("content", message.getMsgContent());
                if (message.getMsgType() == 2 || message.getMsgType() == 4) {
                    json.put("teamId", message.getTeamId());
                    json.put("teamName", message.getTeamName());
                } else if (message.getMsgType() == 3 || message.getMsgType() == 5) {
                    json.put("teamId", message.getTeamId());
                    json.put("teamName", message.getTeamName());
                    json.put("isAgree", message.getDealStatus());
                } else if (message.getMsgType() == 6) {
                    json.put("msgDocId", message.getMsgDocId());
                    json.put("msgDocName", message.getMsgDocName());
                    json.put("content", message.getMsgContent());
                } else if (message.getMsgType() == 7) {
                    json.put("msgDocId", message.getMsgDocId());
                    json.put("msgDocName", message.getMsgDocName());
                }
                jsonList.add(json);
            }
            return res.get(0, jsonList);
        } catch (Exception e) {
            return res.get(-1);
        }
    }

    @PostMapping("/api/message/mark")
    public Map<String, Object> markMessage(@RequestBody Map<String, String> re_map) {
        Response res = new Response();
        String msgId = re_map.get("msgId");
        try {
            messageDao.updateStatusToRead(msgId);
            return res.get(0);
        } catch (Exception e) {
            return res.get(-1);
        }
    }
}
