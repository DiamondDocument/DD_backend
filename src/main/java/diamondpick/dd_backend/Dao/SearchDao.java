package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Entity.Folder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SearchDao {

    /**根据用户信息模糊查找*/
    @Select("select * from users where user_id like '%${value}%' or nickname like '%${value}%'")
    public List<User> selectUser(String value);
    /**根据用户信息模糊查找*/
    @Select("select * from teams where team_id like '%${value}%' or name like '%${value}%'")
    public List<Team> searchTeam(String value);
    /**根据用户信息模糊查找*/
    @Select("select * from documents where doc_id like '%${value}%' or name like '%${value}%'")
    public List<Document> searchDoc(String value);
    /**根据用户信息模糊查找*/
    @Select("select * from folders where folder_id like '%${value}%' or name like '%${value}%'")
    public List<Folder> searchFolder(String value);
}
