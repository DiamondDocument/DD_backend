package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.lyz.Space;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface SpaceDao {
    ArrayList<Space> getMySpace(@Param("userId")  String userId);
    ArrayList<Space> getTeamSpace(@Param("teamId")  String teamId);
    ArrayList<Space> getCollectionSpace(@Param("userId")  String userId);
    ArrayList<Space> getRecycleSpace(@Param("userId")  String userId);
    ArrayList<Space> getLastSpace(@Param("userId")  String userId);

}
