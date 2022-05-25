package diamondpick.dd_backend.Dao.lyz;

import diamondpick.dd_backend.Entity.lyz.Recycle;
import diamondpick.dd_backend.Entity.lyz.Space;
import diamondpick.dd_backend.Entity.lyz.TeamRecycle;
import diamondpick.dd_backend.Entity.lyz.UserSpace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface SpaceDao {

    public void insertSpace(String spaceId);



    ArrayList<Space> getMySpace(@Param("userId")  String userId);
    ArrayList<Space> getTeamSpace(@Param("teamId")  String teamId);
    ArrayList<Space> getCollectionSpace(@Param("userId")  String userId);
    ArrayList<Space> getRecycleSpace(@Param("userId")  String userId);
    ArrayList<Space> getLastSpace(@Param("userId")  String userId);

    void newUserSpace(Space space);

    void newTeamSpace(Space space);

    void newUserRecycle(Recycle recycle);

    void newTeamRecycle(TeamRecycle recycle);

    String getSpaceIdByTeamId(@Param("teamId")  String teamId);
    String getSpaceIdByUserId(@Param("userId")  String userId);

    UserSpace getUserSpace(@Param("userId")  String userId);
}
