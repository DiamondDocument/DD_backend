package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Template;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TemplateDao {

    @Insert("insert into templates(temp_id, name, creator_id, intro) " +
            "values(#{param1},#{param2},#{param3},#{param4}")
    public void insertTemp(String tempId, String name, String creatorId, String intro);

    @Insert("insert into template_collector values(#{param2}, #{param1})")
    public void insertCollection(String collectorId, String tempId);

    @Select("select temp.* , c.nickname as creator_name " +
            "from template as temp, users as c " +
            "where temp.creator_id = c.user_id and " +
            "temp.temp_id = #{param1} ")
    public Template selectTemp(String tempId);

    @Select("select temp.* , c.nickname as creator_name " +
            "from template as temp, users as c " +
            "where temp.creator_id = c.user_id and " +
            "temp.creator_id = #{param1} ")
    public List<Template> selectByCreator(String creatorId);

    //todo
    List<Template> selectByCollector(String userId);
}
