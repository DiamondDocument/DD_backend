package diamondpick.dd_backend.Entity.zzy;

import java.util.Date;

public class Document {

    private String id;
    private String name;
    private String creatorId;
    private Date createTime;
    private String modifierId;
    private Date modifyTime;
    private int selfAuth;
    private int nowAuth;
    private boolean isEditing;
    private String parentId;
    private String spaceId;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}

