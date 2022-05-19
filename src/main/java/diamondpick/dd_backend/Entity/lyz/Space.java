package diamondpick.dd_backend.Entity.lyz;

import java.util.Date;

public class Space {
    String spaceId;
    String spaceName;
    String creatorId;
    Date createTime;
    char spaceType;
    private static int spaceIdCounter = 0;

    public Space() {
    }

    public Space(String spaceName, String creatorId, char spaceType) {
        String spaceId = spaceType + String.format("%06d", spaceIdCounter);
        spaceIdCounter++;
        this.spaceId = spaceId;
        this.spaceName = spaceName;
        this.creatorId = creatorId;
        this.createTime = new Date(System.currentTimeMillis());
        this.spaceType = spaceType;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }


    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public char getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(char spaceType) {
        this.spaceType = spaceType;
    }
}
