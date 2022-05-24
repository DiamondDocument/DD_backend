package diamondpick.dd_backend.Entity.yyh;


public class User {
    private String userId;
    private String nickname;
    private String password;
    private String gender;
    private String intro;
    private String email;


    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getIntro() {
        return intro;
    }

    public String getEmail() {
        return email;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



}
