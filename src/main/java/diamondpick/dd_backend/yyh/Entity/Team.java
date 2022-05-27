package diamondpick.dd_backend.yyh.Entity;

public class Team {

    private String teamId;
    private String captainId;
    private String name;
    private String intro;
    private int spaceId;



    public Team(String teamID, String teamName, String captainID, String teamIntroductory){
        this.teamId =teamID;
        this.name =teamName;
        this.intro =teamIntroductory;
        this.captainId = captainID;
    }
    public Team(){}

    public String getTeamId() {
        return teamId;
    }

    public String getIntro() {
        return intro;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }
}
