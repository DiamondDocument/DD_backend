package diamondpick.dd_backend.Entity;

import java.util.Date;

public class Message {
    /**
     * 额外需要发送者、团队处理中的团队、用户、设计文档的名称
     */
    private String msgId;
    private String senderId;
    private String receiverId;
    private Date sendTime;
    private int msgType;
    private String msgContent;
    private boolean msgStatus;
    private String msgDocId;
    private String msgDealId;
    private String senderName;
    private String receiverName;
    private String msgDocName;
    private String userId;
    private String userName;
    private String teamId;
    private String teamName;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public boolean getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(boolean msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getMsgDocId() {
        return msgDocId;
    }

    public void setMsgDocId(String msgDocId) {
        this.msgDocId = msgDocId;
    }

    public String getMsgDealId() {
        return msgDealId;
    }

    public void setMsgDealId(String msgDealId) {
        this.msgDealId = msgDealId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getMsgDocName() {
        return msgDocName;
    }

    public String getUserId() {
        return userId;
    }

    public String getTeamId() {
        return teamId;
    }
}
