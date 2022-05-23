package diamondpick.dd_backend.Entity.yyh;

import javax.persistence.*;

public class TeamMessage {
    private String teamID;
    private String captainID;
    private String teamName;
    private String teamIntroductory;
    public TeamMessage(String teamID,String teamName,String captainID,String teamIntroductory){
        this.teamID=teamID;
        this.teamName=teamName;
        this.teamIntroductory=teamIntroductory;
        this.captainID = captainID;
    }
    public TeamMessage(){}

    public String getTeamID() {
        return teamID;
    }

    public String getTeamIntroductory() {
        return teamIntroductory;
    }

    public void setTeamID(String teamID) {
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
