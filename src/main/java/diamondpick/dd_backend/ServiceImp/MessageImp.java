package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.MessageDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Entity.Message;
import diamondpick.dd_backend.Service.MessageService;
import diamondpick.dd_backend.Tool.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageImp implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private TeamDao teamDao;

    @Override
    public void newApplyMsg(String senderId, String teamId) {
        try {
            String receiverId = teamDao.selectTeam(teamId).getCaptainId();
            String msgId = new IdGenerator().getId("message");
            messageDao.insertMsg(msgId, senderId, receiverId, 2, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newApplyDealMsg(boolean isAccept, String teamId, String receiverId) {

    }

    @Override
    public void newInviteMsg(String teamId, String receiverId) {

    }

    @Override
    public void newInviteDealMsg(boolean isAccept, String dealerId, String teamId) {

    }

    @Override
    public void newCommentMsg(String senderId, String docId, String receiverId, String comment) {

    }

    @Override
    public void newAtMsg(String senderId, String docId, String receiverId) {

    }

    @Override
    public List<Message> getMsg(String userId, boolean isOnlyUnread) {
        return null;
    }

    @Override
    public void setMsgToRead(String msgId) {

    }
}
