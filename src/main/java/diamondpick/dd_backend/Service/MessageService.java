package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface MessageService {

    /*
   周五之前实现邀请相关
   周五之前实现邀请相关
   周五之前实现邀请相关
    */
    //////////////
    //优先搞邀请的接口
    public boolean newApplyMsg(String senderId, String teamId);

    public boolean newApplyDealMsg(boolean isAccept, String teamId, String receiverId);

    public boolean newInviteMsg(String teamId, String receiverId);

    public boolean newInviteDealMsg(boolean isAccept, String dealerId, String teamId);

    public boolean newCommentMsg(String senderId, String docId, String receiverId);

    public boolean newAtMsg(String senderId, String docId, String receiverId);

    void sendNewMessage(Message sendMessage);

    ArrayList<Message> receiveMessageByUserId(String userId, int status);

    void changeMessageStatus(String userId, int preStatus, int postStatus);

    ArrayList<Message> listMessage(String userId);
}
