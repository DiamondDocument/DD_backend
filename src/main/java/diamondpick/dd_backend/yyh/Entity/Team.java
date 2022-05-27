package diamondpick.dd_backend.yyh.Entity;

public class Team {
    private String teamId;
    private String captainId;
    private String teamName;
    private String teamIntroductory;
    public Team(String teamId, String teamName, String captainId, String teamIntroductory){
        this.teamId=teamId;
        this.teamName=teamName;
        this.teamIntroductory=teamIntroductory;
        this.captainId = captainId;
    }
    public Team(){}

    public String getTeamID() {
        return teamId;
    }

    public String getTeamIntroductory() {
        return teamIntroductory;
    }

    public void setTeamID(String teamId) {
        this.teamId = teamId;
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
