package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.Duplicate.DuplicateId;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.User.PwdError;
import diamondpick.dd_backend.Exception.User.verificationFail;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Exception.NotExist.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public void newUser(String userId, String nickname, String password, String email)throws Illegal, DuplicateId;

    public void login(String userId, String password)throws PwdError, NotExist;

    public void modifyNickname(String userId, String newNickname)throws Illegal, NotExist;
    public void modifyPassword(String userId, String newPwd)throws Illegal, NotExist;
    public void modifyEmail(String userId, String newEmail)throws Illegal, NotExist, verificationFail;
    public void modifyIntro(String userId, String newIntro)throws Illegal, NotExist;

    public List<Team> selectTeams(String userId)throws NotExist;

}
