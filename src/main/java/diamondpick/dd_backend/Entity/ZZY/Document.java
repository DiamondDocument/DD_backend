package diamondpick.dd_backend.Entity.ZZY;


import diamondpick.dd_backend.Entity.yyh.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "documents")
public class Document {
    @Id
    private String id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "creatorId")
    User creator;
//    @Column(columnDefinition = "default now()")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "modifyId")
    User modifier;
//    private String modifyUid;
    private Date modifyTime;

    private Integer selfAuthority;
    private Integer nowAuthority;

    @ManyToOne
    @JoinColumn(name = "fatherId")
    Document father;


//    private String fatherId;


//    public String getCreatorId() {
//        return creatorId;
//    }

//    public void setCreatorId(String creatorId) {
//        this.creatorId = creatorId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    public String getModifyUid() {
//        return modifyUid;
//    }
//

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }
//    public void setModifyUid(String modifyUid) {
//        this.modifyUid = modifyUid;
//    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getSelfAuthority() {
        return selfAuthority;
    }

    public void setSelfAuthority(Integer selfAuthority) {
        this.selfAuthority = selfAuthority;
    }

    public Integer getNowAuthority() {
        return nowAuthority;
    }

    public void setNowAuthority(Integer nowAuthority) {
        this.nowAuthority = nowAuthority;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

//    public String getFatherId() {
//        return fatherId;
//    }
//
//    public void setFatherId(String fatherId) {
//        this.fatherId = fatherId;
//    }
}
