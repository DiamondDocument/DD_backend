package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.Exception.OperationFail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface UserService {
/*
    public void newUser();
    public void login();
    public void modify();


 */

    public ArrayList<Team> selectTeams(String userId);

    //若为null则意味着不存在
    public User selectUserByUserId(String id)throws UserNotExist;
    //向数据库中添加User记录
    public void addUser(User user)throws OperationFail;
    boolean isEmailExist(String email);
    boolean isUserIdExist(String userId);
    boolean isLegalNickname(String nickname);
    boolean isLegalEmail(String email);
    boolean isLegalPassword(String password);
    boolean isLegalUserId(String userId);

    public User selectUserByEmail(String email);

}
