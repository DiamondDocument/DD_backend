package diamondpick.dd_backend.yyh.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

public class RecentBrowse {
    private String uId;
    private int dId;
    private Date browseTime;
    public Date getBrowseTime() {
        return browseTime;
    }
    public int getM_id() {
        return dId;
    }
    public String getU_id() {
        return uId;
    }
    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }
    public void setM_id(int m_id) {
        this.dId = dId;
    }
    public void setU_id(String u_id) {
        this.uId = uId;
    }
}
