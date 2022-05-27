package diamondpick.dd_backend.Old.lyz.Entity;

public class UserSpace extends Space {
    public UserSpace() {
        spaceType = 'u';
    }

    public UserSpace(String spaceName, String creatorId) {
        super(spaceName, creatorId, 'u');
    }
}
