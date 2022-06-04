package diamondpick.dd_backend.Entity;

import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Document implements File {
    @Autowired
    DocumentService documentService;

    private String docId;
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
    private boolean isDelete;
    private String deleterId;
    private Date deleteTime;
    private String creatorName;
    private String modifierName;
    private String deleterName;
    private Date browseTime;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public int getType() {
        return 1;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getFileId() {
        return getDeleterId();
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

    @Override
    public String getSize() {
        try {
            return documentService.getSize(getDocId());
        } catch (OperationFail e) {
            e.printStackTrace();
            System.out.println("不可能");
            return "不可能！";
        }
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

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public String getDeleterName() {
        return deleterName;
    }

    public void setDeleterName(String deleterName) {
        this.deleterName = deleterName;
    }

    public Date getBrowseTime() {
        return browseTime;
    }

}

