package diamondpick.dd_backend.Entity.yyh;



import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class User {
    private String user_id;
    private String nickname;
    private String password;
    private String gender;
    private String user_introductory;
    private String user_email;


    public String getUser_id() {
        return user_id;
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

    public String getUser_introductory() {
        return user_introductory;
    }

    public String getUser_email() {
        return user_email;
    }

    public boolean setUserId(String user_id) {
        if(!user_id.matches("[A-Z]|[a-z]|[0-9]*")) return false;
        this.user_id = user_id;
        return true;
    }

    public boolean setNickname(String nickname) {
        if(!nickname.matches("[A-Z]|[a-z]|[0-9]|[\u4E00-\u9FA5]*")) return false;
        this.nickname = nickname;
        return true;
    }

    public boolean setPassword(String password) {
        if(!password.matches("[A_Z]|[a-z]|[0-9]*")) return false;
        this.password = password;
        return true;
    }

    public boolean setGender(String gender) {
        if(!gender.matches("[男]|[女]|[未知]")) return false;
        this.gender = gender;
        return true;
    }

    public boolean setUser_introductory(String user_introductory) {
        this.user_introductory = user_introductory;
        return true;
    }

    public boolean setUser_email(String user_email) {
        if(!user_email.matches("[A-Z]|[a-z]|[0-9]*@[A-Z]|[a-z]|[0-9]*.[A-Z]|[a-z]|[0-9]")) return false;
        this.user_email = user_email;
        return true;
    }

}
