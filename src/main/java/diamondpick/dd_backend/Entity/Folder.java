package diamondpick.dd_backend.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Folder {
    @Id
    private String folderId;
    private String folderName;
    private String creatorId;
    private Date createTime;
    private String parentId;
    private int selfAuthority;
    private int nowAuthority;

    private static int folderIdCounter = 0;

    public Folder() {
    }

    public Folder(String folderName, String creatorId, Date createTime, String parentId, int selfAuthority, int nowAuthority) {
        String id = "f" + String.format("%06d", folderIdCounter);
        folderIdCounter++;
        this.folderId = id;
        this.folderName = folderName;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.parentId = parentId;
        this.selfAuthority = selfAuthority;
        this.nowAuthority = nowAuthority;
    }

    public String getFolderId() {
        return folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentFolderId) {
        this.parentId = parentFolderId;
    }

    public int getSelfAuthority() {
        return selfAuthority;
    }

    public void setSelfAuthority(int selfAuthority) {
        this.selfAuthority = selfAuthority;
    }

    public int getNowAuthority() {
        return nowAuthority;
    }

    public void setNowAuthority(int nowAuthority) {
        this.nowAuthority = nowAuthority;
    }

    public static int getFolderIdCounter() {
        return folderIdCounter;
    }

    public static void setFolderIdCounter(int folderIdCounter) {
        Folder.folderIdCounter = folderIdCounter;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderId='" + folderId + '\'' +
                ", folderName='" + folderName + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", createTime=" + createTime +
                ", parentId='" + parentId + '\'' +
                ", selfAuthority=" + selfAuthority +
                ", nowAuthority=" + nowAuthority +
                '}';
    }
}
