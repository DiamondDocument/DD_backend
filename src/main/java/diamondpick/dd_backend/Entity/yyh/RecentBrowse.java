package diamondpick.dd_backend.Entity.yyh;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

public class RecentBrowse {
    private String u_id;
    private int d_id;
    private Date browseTime;
    public Date getBrowseTime() {
        return browseTime;
    }
    public int getM_id() {
        return d_id;
    }
    public String getU_id() {
        return u_id;
    }
    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }
    public void setM_id(int m_id) {
        this.d_id = d_id;
    }
    public void setU_id(String u_id) {
        this.u_id = u_id;
    }
}
