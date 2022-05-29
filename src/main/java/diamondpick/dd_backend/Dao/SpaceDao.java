package diamondpick.dd_backend.Dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface SpaceDao {
    /**
     * 分配一个新空间
     */
    @Insert("insert into spaces(name)\n" +
            "values (null)")
    public void insertSpace();

    /**
     * 更新空间的权限
     *
     * @param type         可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     */
    @Update("update spaces\n" +
            "set auth = min(#{param3}, auth)\n" +
            "where space_id in (select space_id from ${param1}s where #{param1}_id = #{param2})")
    public void updateAuth(String type, String spaceOwnerId, int newAuth);

    /**
     * 找到最近创建的空间的spaceId
     */
    @Select("select space_id from spaces\n" +
            "order by create_time desc\n" +
            "limit 1")
    public int selectSpace();

    /**
     * 删除空间
     *
     * @param spaceId
     */
    @Delete("delete from spaces\n" +
            "where space_id = #{param1}")
    public void deleteSpace(int spaceId);

}
