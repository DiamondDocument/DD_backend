package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    //todo 添加定义

    /**
     * 新建申请消息
     *
     * @param senderId 发送者id
     * @param teamId   被申请团队id
     */
    public void newApplyMsg(String senderId, String teamId);

    /**
     * 新建申请处理消息
     *
     * @param isAccept   是否同意
     * @param teamId     团队id
     * @param receiverId 接收者id
     */
    public void newApplyDealMsg(boolean isAccept, String teamId, String receiverId);

    /**
     * 新建邀请消息
     *
     * @param teamId     团队id
     * @param receiverId 接收者id
     */
    public void newInviteMsg(String teamId, String receiverId);

    /**
     * 新建邀请处理消息
     *
     * @param isAccept 是否同意
     * @param dealerId 处理者id
     * @param teamId   团队id
     */
    public void newInviteDealMsg(boolean isAccept, String dealerId, String teamId);

    /**
     * 新建评论消息
     *
     * @param senderId   发送者id
     * @param docId      文档id
     * @param receiverId 接收者id
     * @param comment    评论内容
     */
    public void newCommentMsg(String senderId, String docId, String receiverId, String comment);

    /**
     * 新建@消息
     *
     * @param senderId   发送者id
     * @param docId      文档id
     * @param receiverId 接收者id
     */
    public void newAtMsg(String senderId, String docId, String receiverId);

    /**
     * 查询消息
     *
     * @param userId       用户id
     * @param isOnlyUnread 是否只查询未读消息
     * @return 消息列表
     */
    List<Message> getMsg(String userId, boolean isOnlyUnread);

    /**
     * 标记消息已读
     *
     * @param msgId 消息id
     */
    public void setMsgToRead(String msgId);
}
