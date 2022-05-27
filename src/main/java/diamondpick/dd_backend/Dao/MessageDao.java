package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.lyz.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MessageDao {

    public void insertMsg(String msgId, String senderId, String receiverId, String msgType, String msgContent, String dealId, String docId)throws DuplicateKeyException, DataIntegrityViolationException;

    /**
     * 直接标记已读
     */
    public void updateStatusToRead(String msgId);

    /**
     * @return 返回的Message对象额外需要发送者、团队处理中的团队、用户、文档的名称
     */
    public List<Message> selectMsg(String userId);

    /**
     * @return 返回的Message对象额外需要发送者、团队处理中的团队、用户、文档的名称
     */
    public List<Message>  selectMsgByType(String userId, int MsgType);


}
