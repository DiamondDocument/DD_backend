package diamondpick.dd_backend.Entity.lyz;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String messageId;
    private String senderId;
    private String receiverId;
    private Date sendTime;
    private Date receiveTime;
    private int msgType;
    private String msgContent;
    /* status identifies the current status of the message.
     *  1: successfully send
     *  2: already read*/
    private int msgStatus;
    private String msgDocId;
    private String teamId;
    private int dealStatus;
    private static int folderIdCounter = 0;

    public Message() {
    }

    public Message(String senderId, String receiverId, int msgType, String msgContent, String msgDocId, String teamId, int dealStatus) {
        String id = "m" + String.format("%06d", folderIdCounter++);
        folderIdCounter++;
        this.messageId = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.sendTime = new Date(System.currentTimeMillis());
        this.receiveTime = null;
        this.msgType = msgType;
        this.msgContent = msgContent;
        this.msgStatus = 1;
        this.msgDocId = msgDocId;
        this.teamId = teamId;
        this.dealStatus = dealStatus;
    }

    public String getMessageId() {
        return messageId;
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

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    public int getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getMsgDocId() {
        return msgDocId;
    }

    public void setMsgDocId(String msgDocId) {
        this.msgDocId = msgDocId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }
}
