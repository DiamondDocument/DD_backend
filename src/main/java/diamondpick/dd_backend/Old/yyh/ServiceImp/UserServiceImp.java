package diamondpick.dd_backend.Old.yyh.ServiceImp;

/*
@Service
public class UserServiceImp implements UserService {



    @Autowired
    private UserDao userDao;
// ----------------------------------------------------------

    @Override
    public ArrayList<Team> selectTeams(String userId) {
        throw new RuntimeException();
    }

// ----------------------------------------------------------

    @Override
    public User selectUserByUserId(String id) throws UserNotExist {
        User user = userDao.selectUser(id);
        if(user == null)throw new UserNotExist();
        return user;
    }

    @Override
    public void addUser(User user)throws OperationFail {
        try{
//            userDao.insertUser(user);
        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFail();
        }
    }

    @Override
    public boolean isEmailExist(String email) {
//        return userDao.selectUserBy("email", email).size() == 1;
    }

    @Override
    public boolean isUserIdExist(String userId) {
        return userDao.selectUser(userId) != null;
    }

    @Override
    public boolean isLegalNickname(String nickname) {
        return nickname.matches("[A-Za-z0-9\u4E00-\u9FA5]*");
    }

    @Override
    public boolean isLegalEmail(String email) {
        return email.matches("[A-z0-9]*@[A-z0-9]*.[A-z0-9]*");
    }

    @Override
    public boolean isLegalPassword(String password) {
        return password.matches("[A-z0-9]*");
    }

    @Override
    public boolean isLegalUserId(String userId) {
        return userId.matches("[A-z0-9]*");
    }

    @Override
    public User selectUserByEmail(String email) {
        return userDao.selectUserBy("email", email).get(0);
    }


}


 */