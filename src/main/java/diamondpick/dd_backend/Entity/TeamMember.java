package diamondpick.dd_backend.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TeamMember {
    @Id
    private int teamID;
    private int userID;
    private String rank;
    public TeamMember(){}
    public TeamMember(int teamID,int userID,String rank){
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

    public int getUserID() {
        return userID;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
