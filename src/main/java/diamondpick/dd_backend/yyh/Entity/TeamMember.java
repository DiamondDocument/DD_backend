package diamondpick.dd_backend.yyh.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

public class TeamMember {
    private String teamId;
    private String userId;
    private String userRank;
    public TeamMember(){}
    public TeamMember(String teamId,String userId,String userRank){
        this.teamId = teamId;
        this.userId = userId;
        this.userRank = userRank;
    }

    public void setTeamID(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamID() {
        return teamId;
    }

    public String getUserID() {
        return userId;
    }

    public String getUser_rank() {
        return userRank;
    }

    public void setUser_rank(String rank) {
        this.userRank = rank;
    }

    public void setUserID(String userID) {
        this.userId = userID;
    }
}
