package diamondpick.dd_backend.Service.ImpZzy;

import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User selectUserByUserId(String id) {
        return userDao.selectUserById(id);
    }

    @Override
    public boolean addUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userDao.selectUserBy("user_email", email) != null;
    }

    @Override
    public boolean isUserIdExist(String userId) {
        return userDao.selectUserBy("user_id", userId) != null;
    }

    @Override
    public boolean isLegalNickname(String nickname) {
        return nickname.matches("[A-Z]|[a-z]|[0-9]|[\u4E00-\u9FA5]*");
    }

    @Override
    public boolean isLegalEmail(String email) {
        return email.matches("[A-z]|[0-9]*@[A-z]|[0-9]*.[A-z]|[0-9]");
    }

    @Override
    public boolean isLegalPassword(String password) {
        return password.matches("[A-z]|[0-9]*");
    }

    @Override
    public boolean isLegalUserId(String userId) {
        return userId.matches("[A-z]|[0-9]*");
    }

    @Override
    public User selectUserByEmail(String email) {
        return userDao.selectUserBy("user_email", email).get(0);
    }
}
