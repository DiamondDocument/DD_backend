package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.MessageDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Entity.Message;
import diamondpick.dd_backend.Exception.OperationFail;
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
    public void newTeamDealMsg(int dealId) throws OperationFail {

    }

    @Override
    public void newCommentMsg(String senderId, String docId, String receiverId, String comment) throws OperationFail {

    }

    @Override
    public void newAtMsg(String senderId, String docId, String receiverId) throws OperationFail {

    }

    @Override
    public List<Message> getMsg(String userId, boolean onlyUnread) throws OperationFail {
        return null;
    }

    @Override
    public void setMsgToRead(String msgId) throws OperationFail {

    }
}
