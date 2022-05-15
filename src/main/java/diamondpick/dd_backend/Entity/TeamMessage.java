package diamondpick.dd_backend.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TeamMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamID;
    private String captainID;
    private String teamName;
    private String teamIntroductory;
    public TeamMessage(String teamName,String captainID,String teamIntroductory){
        this.teamName=teamName;
        this.teamIntroductory=teamIntroductory;
        this.captainID = captainID;
    }
    public TeamMessage(){}

    public int getTeamID() {
        return teamID;
    }

    public String getTeamIntroductory() {
        return teamIntroductory;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamIntroductory(String teamIntroductory) {
        this.teamIntroductory = teamIntroductory;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
