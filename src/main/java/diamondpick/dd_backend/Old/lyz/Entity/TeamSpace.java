package diamondpick.dd_backend.Old.lyz.Entity;

public class TeamSpace extends Space {
    public TeamSpace() {
        spaceType = 't';
    }

    public TeamSpace(String spaceName, String creatorId) {
        super(spaceName, creatorId, 't');
    }
}
