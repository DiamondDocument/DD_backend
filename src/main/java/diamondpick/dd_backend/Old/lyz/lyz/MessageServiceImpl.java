package diamondpick.dd_backend.Old.lyz.lyz;

/*
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private TeamDao teamDao;


    @Override
    public boolean newApplyMsg(String senderId, String teamId) {
        try {
            String receiverId = teamDao.selectCaptainID(teamId);
            Message message = new Message(senderId, receiverId, 2, null, null, teamId, 0);
            messageDao.sendNewMessage(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean newApplyDealMsg(boolean isAccept, String dealerId, String teamId) {
        try {
            String senderId = teamDao.selectCaptainID(teamId);
            String receiverId = dealerId;
            Message message;
            if (isAccept)
                message = new Message(senderId, receiverId, 3, null, null, teamId, 1);
            else
                message = new Message(senderId, receiverId, 3, null, null, teamId, 2);
            messageDao.sendNewMessage(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean newInviteMsg(String teamId, String receiverId) {
        try {
            String senderId = teamDao.selectCaptainID(teamId);
            Message message = new Message(senderId, receiverId, 4, null, null, teamId, 0);
            messageDao.sendNewMessage(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean newInviteDealMsg(boolean isAccept, String dealerId, String teamId) {
        try {
            String senderId = dealerId;
            String receiverId = teamDao.selectCaptainID(teamId);
            Message message;
            if (isAccept)
                message = new Message(senderId, receiverId, 5, null, null, teamId, 1);
            else
                message = new Message(senderId, receiverId, 5, null, null, teamId, 2);
            messageDao.sendNewMessage(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean newCommentMsg(String senderId, String docId, String receiverId) {
        try {
            Message message = new Message(senderId, receiverId, 6, null, docId, null, 0);
            messageDao.sendNewMessage(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean newAtMsg(String senderId, String docId, String receiverId) {
        try {
            Message message = new Message(senderId, receiverId, 7, null, docId, null, 0);
            messageDao.sendNewMessage(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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


 */