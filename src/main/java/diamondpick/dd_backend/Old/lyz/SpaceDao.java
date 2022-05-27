package diamondpick.dd_backend.Old.lyz;

import diamondpick.dd_backend.Old.lyz.Entity.Recycle;
import diamondpick.dd_backend.Old.lyz.Entity.Space;
import diamondpick.dd_backend.Old.lyz.Entity.TeamRecycle;
import diamondpick.dd_backend.Old.lyz.Entity.UserSpace;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

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
