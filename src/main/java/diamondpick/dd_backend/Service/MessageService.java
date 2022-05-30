package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Message;
import diamondpick.dd_backend.Exception.OperationFail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    /**
     * 新建申请或者邀请（处理）消息（取决于dealId对应的deal类型和状态）
     * 需要自动按照deal对应来确定发送者和接收者，同意或者拒绝等
     * @param dealId  对应团队处理id
     */
    public void newTeamDealMsg(int dealId)throws OperationFail;

    /**
     * 新建评论消息
     * @param senderId   发送者id
     * @param docId      文档id
     * @param receiverId 接收者id
     * @param comment    评论内容
     */
    public void newCommentMsg(String senderId, String docId, String receiverId, String comment)throws OperationFail;

    /**
     * 新建@消息
     *
     * @param senderId   发送者id
     * @param docId      文档id
     * @param receiverId 接收者id
     */
    public void newAtMsg(String senderId, String docId, String receiverId)throws OperationFail;

    /**
     * 查询消息
     * @param userId       用户id
     * @param onlyUnread 是否只查询未读消息
     * @return 消息列表
     */
    List<Message> getMsg(String userId, boolean onlyUnread)throws OperationFail;

    /**
     * 标记消息已读，若消息已读则不进行任何操作
     * @param msgId 消息id
     */
    public void setMsgToRead(String msgId)throws OperationFail;
}
