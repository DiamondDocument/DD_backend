package diamondpick.dd_backend.Old.lyz.Entity;

public class TeamRecycle extends Recycle {
    private String teamId;

    public TeamRecycle() {
    }

    public TeamRecycle(String fileId, String delId, String preFolderId, String teamId) {
        super(fileId, delId, preFolderId);
        this.teamId = teamId;
    }
}
