package diamondpick.dd_backend.Controller.lyz;

import com.alibaba.fastjson.JSONObject;
import diamondpick.dd_backend.Entity.lyz.Message;
import diamondpick.dd_backend.Entity.yyh.TeamMessage;
import diamondpick.dd_backend.zzy.Entity.Document;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.MessageService;
import diamondpick.dd_backend.Service.TeamService;
import diamondpick.dd_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private DocumentService documentService;

    @GetMapping("/api/message/mark")
    public Map<String, Object> receiveMessage(@RequestParam("userId") String receiverID) {
        Map<String, Object> map = new HashMap<>();
//        try {
//            User userReceiver = userService.selectUserByUserId(receiverID);
//            if (userReceiver == null) {
//                map.put("error", 1);
//                map.put("message", "用户不存在：接收者不存在");
//            } else {
//                ArrayList<Message> messages;
//                messages = messageService.receiveMessageByUserId(receiverID, 1);
//                if (messages.size() == 0) {
//                    map.put("error", 0);
//                    map.put("message", "当前用户没有新通知");
//                } else {
//                    ArrayList<JSONObject> jsonList = new ArrayList<>();
//                    for (Message message : messages) {
//                        JSONObject jsonObject = new JSONObject();
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
//                        jsonObject.put("content", message.getMessage());
//                        jsonObject.put("date", formatter.format(message.getDateCreated()));
//                        jsonList.add(jsonObject);
//                    }
//                    map.put("error", 0);
//                    map.put("message", jsonList);
//                    messageService.changeMessageStatus(receiverID, 1, 2);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("error", -1);
//            map.put("message", "其他错误");
//        }
        return map;
    }

    @GetMapping("/api/message/list")
    public Map<String, Object> listMessage(@RequestParam("userId") String userId, @RequestParam("msgType") int msgType) {
        Map<String, Object> map = new HashMap<>();
        try {
            ArrayList<JSONObject> jsonList = new ArrayList<>();
            ArrayList<Message> messages = messageService.listMessage(userId);
            map.put("code", 0);
            for (Message message : messages) {
                if (msgType != 0 && message.getMsgType() != msgType)
                    continue;
                JSONObject json = new JSONObject();
                json.put("msgId", message.getMessageId());
                json.put("msgType", message.getMsgType());
                json.put("senderId", message.getSenderId());
                json.put("senderName", userService.selectUserByUserId(message.getSenderId()).getNickname());
                json.put("sendTime", message.getSendTime());
                if (message.getMsgType() == 2 || message.getMsgType() == 3 || message.getMsgType() == 4 || message.getMsgType() == 5) {
                    TeamMessage teamMessage = teamService.selectTeamByTeamId(message.getTeamId());
                    json.put("teamName", teamMessage.getTeamName());
                }
                if (message.getMsgType() == 6 || message.getMsgType() == 7) {
                    Document document = documentService.selectDocByDocId(message.getMsgDocId());
                    json.put("msgDocName", document.getName());
                }
                jsonList.add(json);
            }
            map.put("error", 0);
            map.put("msg", jsonList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("message", "其他错误");
        }
        return map;
    }

    @PostMapping("/api/message/send")
    public Map<String, Object> sendMessage(@RequestBody Map<String, String> requestMap) {
        String senderID = requestMap.get("senderId");
        String receiverID = requestMap.get("receiverId");
        String messageContent = requestMap.get("message");
        Map<String, Object> map = new HashMap<>();
//        try {
//            User userSender = userService.selectUserByUserId(senderID);
//            User userReceiver = userService.selectUserByUserId(receiverID);
//            if (userSender == null) {
//                map.put("error", 1);
//                map.put("message", "用户不存在：发送者不存在");
//            } else if (userReceiver == null) {
//                map.put("error", 1);
//                map.put("msg", "用户不存在：接收者不存在");
//            } else {
//                Message sendMessage = new Message(senderID, receiverID, messageContent, new Date(System.currentTimeMillis()), 1);
//                messageService.sendNewMessage(sendMessage);
//                map.put("error", 0);
//                map.put("message", "发送成功");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("error", -1);
//            map.put("message", "其他错误");
//        }
        return map;
    }
}
