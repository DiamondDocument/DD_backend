package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.lyz.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface MessageDao {
    void sendNewMessage(Message sendMessage);
    ArrayList<Message> receiveMessageByUserId(@Param("userId")  String userId, @Param("status") int status);
    void changeMessageStatus(@Param("userId") String userId, @Param("preStatus") int preStatus, @Param("postStatus") int postStatus);

    ArrayList<Message> listMessage(@Param("userId") String userId);
}
