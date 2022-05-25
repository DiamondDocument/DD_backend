package diamondpick.dd_backend.zzy.Entity;

import java.util.Date;

public class RecentDocument extends Document{
    private Date browseTime;

    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }
}
