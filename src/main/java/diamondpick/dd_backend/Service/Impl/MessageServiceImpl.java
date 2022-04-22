package diamondpick.dd_backend.Service.Impl;

import diamondpick.dd_backend.Entity.Message;
import diamondpick.dd_backend.Service.MessageService;
import diamondpick.dd_backend.Dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;
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
}
