package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.yyh.Team;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Exception.NotExist.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface UserService {


    //若为null则意味着不存在
    public User selectUserByUserId(String id)throws UserNotExist;
    //插入失败则返回false
    public boolean addUser(User user);
    boolean isEmailExist(String email);
    boolean isUserIdExist(String userId);
    boolean isLegalNickname(String nickname);
    boolean isLegalEmail(String email);
    boolean isLegalPassword(String password);
    boolean isLegalUserId(String userId);

    User selectUserByEmail(String email);

    public ArrayList<Team> selectTeams(String userId);

}
