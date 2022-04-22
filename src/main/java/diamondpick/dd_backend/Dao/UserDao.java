package diamondpick.dd_backend.Dao;


import diamondpick.dd_backend.Entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    public void InsertNewUser(User user);
    public User SelectUserById(String user_id);
    public List<User> SelectUserByName(String user_id);

}
