package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.yyh.Team;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Exception.Illegal.EmailIllegal;
import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.Exception.OperationFail;
import org.springframework.stereotype.Service;

import javax.management.openmbean.OpenDataException;
import java.util.ArrayList;

@Service
public interface UserService {


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

    public ArrayList<Team> selectTeams(String userId);

}
