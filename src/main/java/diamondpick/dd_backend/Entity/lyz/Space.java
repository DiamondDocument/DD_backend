package diamondpick.dd_backend.Entity.lyz;

public class Space {
    private String spaceId;
    private String spaceName;
    private String creatorId;
    private String createTime;
    private char spaceType;
    private int spaceIdCounter = 0;

    public Space() {
    }

    public Space(String spaceName, String creatorId, String createTime, char spaceType) {
        String spaceId = spaceType + String.format("%06d", spaceIdCounter);
        spaceIdCounter++;
        this.spaceId = spaceId;
        this.spaceName = spaceName;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.spaceType = spaceType;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }


    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public char getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(char spaceType) {
        this.spaceType = spaceType;
    }

    public int getSpaceIdCounter() {
        return spaceIdCounter;
    }

    public void setSpaceIdCounter(int spaceIdCounter) {
        this.spaceIdCounter = spaceIdCounter;
    }
}
