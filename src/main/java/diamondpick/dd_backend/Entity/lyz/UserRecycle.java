package diamondpick.dd_backend.Entity.lyz;

import java.util.Date;

public class UserRecycle extends Recycle {
    private String userId;

    public UserRecycle() {
    }

    public UserRecycle(String fileId, String delId, Date delTime, String preFolderId, String userId) {
        super(fileId, delId, delTime, preFolderId);
        this.userId = userId;
    }
}
