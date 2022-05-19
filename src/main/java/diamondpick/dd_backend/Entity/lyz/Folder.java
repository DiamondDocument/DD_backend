package diamondpick.dd_backend.Entity.lyz;

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

    // 文件生成时的权限
    private int selfAuth;

    // 文件结合其父文件生成的权限
    private int nowAuth;

    // 项目启动时查询数据库表中已有的文件夹数量
    private static int folderIdCounter = 0;

    public Folder() {
    }

    public Folder(String folderName, String creatorId, Date createTime, String parentId, int selfAuth, int nowAuth) {
        String id = "f" + String.format("%06d", folderIdCounter);
        folderIdCounter++;
        this.folderId = id;
        this.folderName = folderName;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.parentId = parentId;
        this.selfAuth = selfAuth;
        this.nowAuth = nowAuth;
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

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSelfAuth() {
        return selfAuth;
    }

    public void setSelfAuth(int selfAuth) {
        this.selfAuth = selfAuth;
    }

    public int getNowAuth() {
        return nowAuth;
    }

    public void setNowAuth(int nowAuth) {
        this.nowAuth = nowAuth;
    }
}
