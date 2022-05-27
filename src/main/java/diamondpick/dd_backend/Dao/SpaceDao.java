package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.lyz.Recycle;
import diamondpick.dd_backend.Entity.lyz.Space;
import diamondpick.dd_backend.Entity.lyz.TeamRecycle;
import diamondpick.dd_backend.Entity.lyz.UserSpace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface SpaceDao {
    /**分配一个新空间*/
    public void insertSpace();

    /**找到最近创建的空间的spaceId*/
    public int selectSpace();

    public void deleteSpace(int spaceId);

}
