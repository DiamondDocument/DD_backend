package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.SpaceDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Exception.Duplicate.DuplicateId;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.Illegal.NameIllegal;
import diamondpick.dd_backend.Exception.Illegal.PwdIllegal;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Exception.User.PwdError;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Service.ConstraintService;
import diamondpick.dd_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImp implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    SpaceDao spaceDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    ConstraintService constraintService;

    @Override
    public void newUser(String userId, String nickname, String password, String email) throws NameIllegal, PwdIllegal,  DuplicateId, OtherFail {
        if(userDao.selectUser(userId) != null || userDao.selectUserByEmail(email) != null) throw new DuplicateId();
        constraintService.checkName(nickname);
        constraintService.checkPassword(password);
        try{
            constraintService.checkUserId(userId);
        }catch (Exception e){
            throw new OtherFail();
        }
        spaceDao.insertSpace();
        try{
            userDao.insertUser(userId, nickname, password, null, email, spaceDao.selectSpace());
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            spaceDao.deleteSpace(spaceDao.selectSpace());
            throw new DuplicateId();
        }
    }

    /**只要正常返回就算登陆成功*/
    @Override
    public User login(String userId, String email, String password) throws PwdError, NotExist, OtherFail {
        if(email == null && userId == null || email != null && userId != null)throw new OtherFail();
        User user = userDao.selectUser(userId);
        if(user == null)throw new NotExist();
        if(!user.getPassword().equals(password))throw new PwdError();
        return user;
    }

    @Override
    public void modifyNickname(String userId, String newNickname) throws Illegal, NotExist, OtherFail {
        if( userDao.selectUser(userId) == null )throw new NotExist();
        constraintService.checkName(newNickname);
        try{
            userDao.updateUser(userId, "nickname", newNickname);
        }catch (DataIntegrityViolationException e){
            throw new Illegal();
        }
    }

    @Override
    public void modifyPassword(String userId, String newPwd) throws Illegal, NotExist, OtherFail {
        if(userDao.selectUser(userId) == null)throw new NotExist();
        constraintService.checkPassword(newPwd);
        try{
            userDao.updateUser(userId, "password", newPwd);
        }catch (DataIntegrityViolationException e){
            throw new Illegal();
        }
    }

    @Override
    public void modifyEmail(String userId, String newEmail) throws Illegal, NotExist, OtherFail {
        if( userDao.selectUser(userId) == null )throw new NotExist();
        try{
            userDao.updateUser(userId, "password", newEmail);
        }catch (DataIntegrityViolationException e){
            throw new Illegal();
        }
    }

    @Override
    public void modifyIntro(String userId, String newIntro) throws Illegal, NotExist, OtherFail {
        if( userDao.selectUser(userId) == null )throw new NotExist();
        constraintService.checkIntro(newIntro);
        try{
            userDao.updateUser(userId, "intro", newIntro);
        }catch (DataIntegrityViolationException e){
            throw new Illegal();
        }
    }
    @Override
    public List<Team> selectTeams(String userId) throws NotExist, OtherFail {
        if(userDao.selectUser(userId) == null)throw new NotExist();
        List<Team> teamList = teamDao.selectTeamByCaptain(userId);
        teamList.addAll(teamDao.selectTeamByMember(userId));
        return teamList;
    }


}
