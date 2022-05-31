package diamondpick.dd_backend.Entity;

import java.util.Date;

public class Message {
    /**
     * 额外需要发送者名称、相关文档名称、相关团队名称、处理结果（如果是邀请、申请处理通知）
     */
    private String msgId;
    private String senderId;
    private String senderName;
    private String receiverId;
    private Date sendTime;
    private int msgType;
    private String msgContent;
    private boolean msgStatus;
    private String msgDocId;
    private String msgDealId;
    private String msgDocName;
    private String teamId;
    private String teamName;
    private int dealStatus;

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

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public boolean isMsgStatus() {
        return msgStatus;
    }

    public String getMsgDocName() {
        return msgDocName;
    }

    public void setMsgDocName(String msgDocName) {
        this.msgDocName = msgDocName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }
}
