package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface MessageDao {
    void sendNewMessage(Message sendMessage);

    ArrayList<Message> receiveMessageByUserId(@Param("userId") int userId, @Param("status") int status);

    void changeMessageStatus(@Param("userId") int userId, @Param("preStatus") int preStatus, @Param("postStatus") int postStatus);
}
