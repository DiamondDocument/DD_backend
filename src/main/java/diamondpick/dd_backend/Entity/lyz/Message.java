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
    private int messageID;

    private String senderID;
    private String receiverID;
    private String message;
    private Date dateCreated;
    /* status identifies the current status of the message.
    *  1: successfully send
    *  2: already read*/
    private int status;

    public Message() {
    }
    public Message(String senderID, String receiverID, String message, Date dateCreated, int status) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
