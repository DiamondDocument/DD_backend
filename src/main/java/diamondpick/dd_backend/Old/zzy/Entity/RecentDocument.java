package diamondpick.dd_backend.Old.zzy.Entity;

import diamondpick.dd_backend.Entity.Document;

import java.util.Date;

public class RecentDocument extends Document {
    private Date browseTime;

    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }
}
