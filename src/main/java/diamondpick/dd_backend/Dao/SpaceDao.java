package diamondpick.dd_backend.Dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpaceDao {
    /**分配一个新空间*/
    public void insertSpace();

    /** 更新空间的权限
     * @param type 可以是"team"和"user"的一种，表示是团队空间还是用户空间
     * @param spaceOwnerId 空间所有者的id（可能是团队id或者用户id）
     */
    public void updateAuth(String type, String spaceOwnerId);

    /**找到最近创建的空间的spaceId*/
    public int selectSpace();

    public void deleteSpace(int spaceId);

}
