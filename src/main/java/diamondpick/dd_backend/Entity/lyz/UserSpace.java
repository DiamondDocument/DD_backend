package diamondpick.dd_backend.Entity.lyz;

import java.util.Date;

public class UserSpace extends Space {
    public UserSpace() {
        spaceType = 'u';
    }

    public UserSpace(String spaceName, String creatorId, Date createTime) {
        super(spaceName, creatorId, createTime, 'u');
    }
}
