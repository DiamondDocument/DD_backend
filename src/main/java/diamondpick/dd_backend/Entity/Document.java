package diamondpick.dd_backend.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.print.Doc;
import java.sql.Date;

@Entity
public class Document {
    @Id
    private String id;

    private String CreateUid;

    private Date CreateTime;
    private String ModifyUid;

    private Date ModifyTime;
    private Integer SelfAuthority;
    private Integer NowAuthority;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateUid() {
        return CreateUid;
    }

    public void setCreateUid(String createUid) {
        CreateUid = createUid;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public String getModifyUid() {
        return ModifyUid;
    }

    public void setModifyUid(String modifyUid) {
        ModifyUid = modifyUid;
    }

    public Date getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        ModifyTime = modifyTime;
    }

    public Integer getSelfAuthority() {
        return SelfAuthority;
    }

    public void setSelfAuthority(Integer selfAuthority) {
        SelfAuthority = selfAuthority;
    }

    public Integer getNowAuthority() {
        return NowAuthority;
    }

    public void setNowAuthority(Integer nowAuthority) {
        NowAuthority = nowAuthority;
    }

    public Document(){}
    public Document(String id, String createUid, Date createTime,
                    String modifyUid, Date modifyTime, Integer selfAuthority, Integer nowAuthority) {
        this.id = id;
        CreateUid = createUid;
        CreateTime = createTime;
        ModifyUid = modifyUid;
        ModifyTime = modifyTime;
        SelfAuthority = selfAuthority;
        NowAuthority = nowAuthority;
    }
}
