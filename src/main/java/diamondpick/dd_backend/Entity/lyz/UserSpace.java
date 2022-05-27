package diamondpick.dd_backend.Entity.lyz;

public class UserSpace extends Space {
    public UserSpace() {
        spaceType = 'u';
    }

    public UserSpace(String spaceName, String creatorId) {
        super(spaceName, creatorId, 'u');
    }
}
