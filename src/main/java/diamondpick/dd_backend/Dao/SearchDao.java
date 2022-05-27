package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Entity.Folder;

import java.util.List;

public interface SearchDao {
    //根据用户信息模糊查找所有用户
    public List<User> selectUserVaguely(String value);
    public List<Team> searchTeam(String value);
    public List<Document> searchDoc(String value);
    public List<Folder> searchFolder(String value);
}
