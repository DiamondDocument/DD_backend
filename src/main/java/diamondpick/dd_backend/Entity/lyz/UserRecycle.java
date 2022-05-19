package diamondpick.dd_backend.Entity.lyz;

import java.util.Date;

public class UserRecycle extends Recycle {
    private String userId;

    public UserRecycle() {
    }

    public UserRecycle(String fileId, String delId, String preFolderId, String userId) {
        super(fileId, delId, preFolderId);
        this.userId = userId;
    }
}
