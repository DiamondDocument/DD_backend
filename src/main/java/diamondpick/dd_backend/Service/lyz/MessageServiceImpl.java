package diamondpick.dd_backend.Service.lyz;

import diamondpick.dd_backend.Entity.lyz.Message;
import diamondpick.dd_backend.Service.MessageService;
import diamondpick.dd_backend.Dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public boolean newInviteMsg(String teamId, String receiverId) {
        return false;
    }

    @Override
    public boolean newInviteDealMsg(boolean isAccept, String dealerId, String teamId) {

        return isAccept;
    }

    @Override
    public boolean newApplyDealMsg(boolean isAccept, String dealerId, String teamId) {
        return false;
    }

    @Override
    public boolean newApplyMsg(String senderId, String teamId) {
        return false;
    }

    @Override
    public boolean newAtMsg(String senderId, String docId, String receiverId) {
        return false;
    }

    @Override
    public boolean newCommentMsg(String senderId, String docId) {
        return false;
    }

    @Override
    public void sendNewMessage(Message sendMessage) {
        messageDao.sendNewMessage(sendMessage);
    }

    @Override
    public ArrayList<Message> receiveMessageByUserId(String userId, int status) {
        return messageDao.receiveMessageByUserId(userId, status);
    }

    @Override
    public void changeMessageStatus(String userId, int preStatus, int postStatus) {
        messageDao.changeMessageStatus(userId, preStatus, postStatus);
    }

    @Override
    public ArrayList<Message> listMessage(String userId) {
        return messageDao.listMessage(userId);
    }
}
