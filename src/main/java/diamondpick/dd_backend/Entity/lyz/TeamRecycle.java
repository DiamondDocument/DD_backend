package diamondpick.dd_backend.Entity.lyz;

import java.util.Date;

public class TeamRecycle extends Recycle {
    private int teamId;

    public TeamRecycle() {
    }

    public TeamRecycle(String fileId, String delId, Date delTime, String preFolderId, int teamId) {
        super(fileId, delId, delTime, preFolderId);
        this.teamId = teamId;
    }
}
