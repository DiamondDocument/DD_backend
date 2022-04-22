package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Message;

import java.util.ArrayList;
import java.util.Date;

public interface MessageService {
    void sendNewMessage(Message sendMessage);
    ArrayList<Message> receiveMessageByUserId(int userId, int status);
    void changeMessageStatus(int userId, int preStatus, int postStatus);
}
