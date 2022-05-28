package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface MessageService {
    //todo 添加定义
    public void newApplyMsg(String senderId, String teamId);

    public void newApplyDealMsg(boolean isAccept, String teamId, String receiverId);

    public void newInviteMsg(String teamId, String receiverId);

    public void newInviteDealMsg(boolean isAccept, String dealerId, String teamId);

    public void newCommentMsg(String senderId, String docId, String receiverId, String comment);

    public void newAtMsg(String senderId, String docId, String receiverId);

    List<Message> getMsg(String userId, boolean isOnlyUnread);

    public void setMsgToRead(String msgId);

}
