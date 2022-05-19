package diamondpick.dd_backend.Entity.lyz;

import java.util.Date;

public class Recycle {
    String fileId;
    String delId;
    Date delTime;
    String preFolderId;

    public Recycle() {
    }

    public Recycle(String fileId, String delId, Date delTime, String preFolderId) {
        this.fileId = fileId;
        this.delId = delId;
        this.delTime = delTime;
        this.preFolderId = preFolderId;
    }
}
