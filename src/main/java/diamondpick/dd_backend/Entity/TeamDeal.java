package diamondpick.dd_backend.Entity;

import java.util.Date;

public class TeamDeal {
    private int dealId;
    private String teamId;
    private String teamName;
    private String userId;
    private String userName;
    private int dealType;
    private Date createTime;
    private int dealStatus;

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }
}
