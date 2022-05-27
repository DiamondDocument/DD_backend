package diamondpick.dd_backend.Dao.lyz;

import diamondpick.dd_backend.Entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface MessageDao {
    void sendNewMessage(Message sendMessage);
    ArrayList<Message> receiveMessageByUserId(@Param("userId")  String userId, @Param("status") int status);
    void changeMessageStatus(@Param("userId") String userId, @Param("preStatus") int preStatus, @Param("postStatus") int postStatus);

    ArrayList<Message> listMessage(@Param("userId") String userId);
}
