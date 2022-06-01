package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Exception.Duplicate.DuplicateId;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.Illegal.NameIllegal;
import diamondpick.dd_backend.Exception.Illegal.PwdIllegal;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Exception.User.PwdError;
import diamondpick.dd_backend.Exception.User.verificationFail;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Exception.NotExist.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public void newUser(String userId, String nickname, String password, String email)throws NameIllegal, PwdIllegal, DuplicateId, OtherFail;

    public User login(String userId, String email, String password)throws PwdError, NotExist, OtherFail;

    public void modifyNickname(String userId, String newNickname)throws Illegal, NotExist, OtherFail;
    public void modifyPassword(String userId, String newPwd)throws Illegal, NotExist, OtherFail;
    public void modifyEmail(String userId, String newEmail)throws Illegal, NotExist, verificationFail, OtherFail;
    public void modifyIntro(String userId, String newIntro)throws Illegal, NotExist, OtherFail;

    public List<Team> selectTeams(String userId)throws NotExist, OtherFail;

}
