package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface MessageService {

    //优先搞邀请的接口
    public boolean newInviteMsg(String teamId, String receiverId);
    public void newInviteDealMsg(boolean isAccept, String dealerId, String teamId);


    public boolean newApplyMsg(String senderId, String teamId);

    void sendNewMessage(Message sendMessage);
    ArrayList<Message> receiveMessageByUserId(String userId, int status);
    void changeMessageStatus(String userId, int preStatus, int postStatus);

    ArrayList<Message> listMessage(String userId);
}
