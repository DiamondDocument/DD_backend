package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.MessageDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Dao.TeamDealDao;
import diamondpick.dd_backend.Entity.Message;
import diamondpick.dd_backend.Entity.TeamDeal;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.MessageService;
import diamondpick.dd_backend.Tool.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageImp implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private TeamDealDao teamDealDao;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public void newTeamDealMsg(int dealId) throws OperationFail {
        TeamDeal deal = teamDealDao.selectDealByDealId(dealId);
        if (deal == null) {
            throw new OperationFail();
        }
        if (deal.getDealType() == 0) {
            // 邀请
            String msgId = idGenerator.generateId('m');
            String senderId;
            String receiverId;
            int msgType;
            if (deal.getDealStatus() == 0) {
                // 发送邀请信息
                senderId = teamDao.selectTeam(deal.getTeamId()).getCaptainId();
                receiverId = deal.getUserId();
                msgType = 4;
            } else {
                // 发送同意或者拒绝信息
                senderId = deal.getUserId();
                receiverId = teamDao.selectTeam(deal.getTeamId()).getCaptainId();
                msgType = 5;
            }
            messageDao.insertMsg(msgId, senderId, receiverId, msgType, null, null, dealId);
        } else if (deal.getDealType() == 1) {
            // 申请
            String msgId = idGenerator.generateId('m');
            String senderId;
            String receiverId;
            int msgType;
            if (deal.getDealStatus() == 0) {
                // 发送申请信息
                senderId = deal.getUserId();
                receiverId = teamDao.selectTeam(deal.getTeamId()).getCaptainId();
                msgType = 2;
            } else {
                // 发送同意或者拒绝信息
                senderId = teamDao.selectTeam(deal.getTeamId()).getCaptainId();
                receiverId = deal.getUserId();
                msgType = 3;
            }
            messageDao.insertMsg(msgId, senderId, receiverId, msgType, null, null, dealId);
        } else {
            throw new OperationFail();
        }
    }

    @Override
    public void newCommentMsg(String senderId, String docId, String receiverId, String comment) throws OperationFail {
        try {
            messageDao.insertMsg(idGenerator.generateId('m'), senderId, receiverId, 6, comment, docId, 0);
        } catch (Exception e) {
            throw new OperationFail();
        }
    }

    @Override
    public void newAtMsg(String senderId, String docId, String receiverId) throws OperationFail {
        try {
            messageDao.insertMsg(idGenerator.generateId('m'), senderId, receiverId, 7, null, docId, 0);
        } catch (Exception e) {
            throw new OperationFail();
        }
    }

    @Override
    public List<Message> getMsg(String userId, boolean onlyUnread) throws OperationFail {
        List<Message> msgList = new ArrayList<>();
        try {
            List<Message> messages = messageDao.selectMsg(userId);
            if (onlyUnread) {
                for (Message message : messages) {
                    if (!message.getMsgStatus()) {
                        msgList.add(message);
                    }
                }
            } else {
                msgList = messages;
            }
        } catch (Exception e) {
            throw new OperationFail();
        }
        return msgList;
    }

    @Override
    public void setMsgToRead(String msgId) throws OperationFail {
        try {
            messageDao.updateStatusToRead(msgId);
        } catch (Exception e) {
            throw new OperationFail();
        }
    }
}
