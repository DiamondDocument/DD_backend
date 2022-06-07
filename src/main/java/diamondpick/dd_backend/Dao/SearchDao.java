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
//todo 可能需要再优化一下
    /**根据用户信息模糊查找*/
    @Select("select * from users where user_id like '%${value}%' or nickname like '%${value}%'")
    public List<User> searchUser(String value);
    /**根据用户信息模糊查找*/
    @Select("select * from teams where team_id like '%${value}%' or name like '%${value}%'")
    public List<Team> searchTeam(String value);
//    /**根据用户信息模糊查找*/
//    @Select("select * from documents where doc_id like '%${value}%' or name like '%${value}%'")
//    public List<Document> searchDoc(String value);
    /**根据用户信息模糊查找*/
    @Select("select * from folders where folder_id like '%${value}%' or name like '%${value}%'")
    public List<Folder> searchFolder(String value);

    @Select("select\n" +
            "    doc2.*, m.nickname as modifier_name\n" +
            "    from  users m right join\n" +
            "        (select doc.*, c.nickname as creator_name from  documents doc, users c, ${param1}s s\n" +
            "            where doc.creator_id = c.user_id and\n" +
            "                  doc.space_id = s.space_id and\n" +
            "                  doc.is_delete = false and\n" +
            "                  s.${param1}_id = #{param2} and\n" +
            "                  doc.doc_id like '%${param3}%' or doc.name like '%${param3}%' ) doc2" +
            "    on doc2.modifier_id = m.user_id\n" )
    public List<Document> searchDoc(String type, String spaceOwnerId, String key);
}
