package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.Template;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TemplateDao {

    @Insert("insert into templates(temp_id, name, creator_id, intro) " +
            "values(#{param1},#{param2},#{param3},#{param4})")
    public void insertTemp(String tempId, String name, String creatorId, String intro);

    @Insert("insert into template_collector values(#{param2}, #{param1})")
    public void insertCollection(String collectorId, String tempId);

    @Delete("delete from template_collector where collector_id = #{param1} and temp_id = #{param2}")
    public void deleteCollection(String collectorId, String tempId);

    @Select("select temp.* , c.nickname as creator_name " +
            "from templates as temp, users as c " +
            "where temp.creator_id = c.user_id and " +
            "temp.temp_id = #{param1} ")
    public Template selectTemp(String tempId);

    @Select("select temp.* , c.nickname as creator_name " +
            "from templates as temp, users as c " +
            "where temp.creator_id = c.user_id and " +
            "temp.creator_id = #{param1} ")
    public List<Template> selectByCreator(String creatorId);

    @Select("select temp.* , c.nickname as creator_name " +
            "from templates as temp, users as c, template_collector as co " +
            "where temp.creator_id = c.user_id and " +
            "temp.temp_id = co.temp_id and " +
            "co.collector_id = #{param1}")
    public List<Template> selectCollection(String collectorId);

    @Select("select temp_id from template_collector where temp_id = #{param2} and collector_id = #{param1}")
    public String selectCollectorAndTemp(String collectorId, String tempId);

    @Select("select DISTINCT temp_id\n" +
            "from templates\n" +
            "order by convert(temp_id using gbk) desc\n" +
            "limit 1")
    public String selectMaxId();

}
