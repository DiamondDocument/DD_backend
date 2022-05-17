package diamondpick.dd_backend.Entity.yyh;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TeamMember {
    @Id
    private int teamID;
    private String userID;
    private String user_rank;
    public TeamMember(){}
    public TeamMember(int teamID,String userID,String user_rank){
        this.teamID = teamID;
        this.userID = userID;
        this.user_rank = user_rank;
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

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String rank) {
        this.user_rank = rank;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
