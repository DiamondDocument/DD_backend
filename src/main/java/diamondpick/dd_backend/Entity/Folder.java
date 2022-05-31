package diamondpick.dd_backend.Entity;

import diamondpick.dd_backend.Exception.NotExist.DocNotExist;

import java.util.Date;

public class Folder implements File {
    /**
     * 还要额外包括创建者的名称、删除者的名称
     */
    private String folderId;
    private String name;
    private String creatorId;
    private Date createTime;
    private String parentId;
    private String spaceId;
    private int selfAuth;
    private int nowAuth;
    private boolean isDelete;
    private String deleterId;
    private Date deleteTime;
    private String creatorName;
    private String deleterName;

    @Override
    public String getFileId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    @Override
    public int getType() {
        return 2;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getDeleterId() {
        return deleterId;
    }

    public void setDeleterId(String deleterId) {
        this.deleterId = deleterId;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public String getCreatorName() {
        return creatorName;
    }

    @Override
    public String getDeleterName() {
        return deleterName;
    }

    @Override
    public String getSize() throws DocNotExist {
        return null;
    }

    @Override
    public String getModifierName() {
        return null;
    }

    @Override
    public String getModifierId() {
        return null;
    }

    @Override
    public Date getModifyTime() {
        return null;
    }
}
