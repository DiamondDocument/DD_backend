package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public interface MessageService {
    void sendNewMessage(Message sendMessage);
    ArrayList<Message> receiveMessageByUserId(String userId, int status);
    void changeMessageStatus(String userId, int preStatus, int postStatus);
}
