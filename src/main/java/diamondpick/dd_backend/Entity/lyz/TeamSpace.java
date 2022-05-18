package diamondpick.dd_backend.Entity.lyz;

import java.util.Date;

public class TeamSpace extends Space {
    public TeamSpace() {
        spaceType = 't';
    }

    public TeamSpace(String spaceName, String creatorId, Date createTime) {
        super(spaceName, creatorId, createTime, 't');
    }
}
