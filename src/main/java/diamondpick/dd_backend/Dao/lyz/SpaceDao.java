package diamondpick.dd_backend.Dao.lyz;

import diamondpick.dd_backend.Entity.lyz.Recycle;
import diamondpick.dd_backend.Entity.lyz.Space;
import diamondpick.dd_backend.Entity.lyz.TeamRecycle;
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

    void newUserSpace(Space space);

    void newTeamSpace(Space space);

    void newUserRecycle(Recycle recycle);

    void newTeamRecycle(TeamRecycle recycle);
}
