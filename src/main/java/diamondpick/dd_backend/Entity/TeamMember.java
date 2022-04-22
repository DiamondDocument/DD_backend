package diamondpick.dd_backend.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TeamMember {
    @Id
    private int teamID;
    private String userID;
    private String rank;
    public TeamMember(){}
    public TeamMember(int teamID,String userID,String rank){
        this.teamID = teamID;
        this.userID = userID;
        this.rank = rank;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getTeamID() {
        return teamID;
    }

    public String getUserID() {
        return userID;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
