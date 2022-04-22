package diamondpick.dd_backend.Service.ImpZzy;

import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Service.UserService;

public class UserServiceImp implements UserService {
    @Override
    public User selectUserByUserId(String id) {
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean isEmailExist(String email) {
        return false;
    }

    @Override
    public boolean isUserIdExist(String userId) {
        return false;
    }

    @Override
    public boolean isLegalNickname(String nickname) {
        return false;
    }

    @Override
    public boolean isLegalEmail(String email) {
        return false;
    }

    @Override
    public boolean isLegalPassword(String password) {
        return false;
    }

    @Override
    public boolean isLegalUserId(String userId) {
        return false;
    }

    @Override
    public User selectUserByEmail(String email) {
        return null;
    }
}
