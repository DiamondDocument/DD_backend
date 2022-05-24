package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.TeamRecycle;
import diamondpick.dd_backend.Entity.lyz.TeamSpace;
import diamondpick.dd_backend.Entity.lyz.UserRecycle;
import diamondpick.dd_backend.Entity.lyz.UserSpace;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.zzy.Exception.SpaceNotExist;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface SpaceService {

     /*
    周五之前实现
    周五之前实现
    周五之前实现
     */
    //////////////
    //返回类型改成对应的
    public UserSpace newUserSpace(String userId, String userSpaceName);
    public TeamSpace newTeamSpace(String teamId, String teamSpaceName);
    public UserRecycle newUserRecycle(String fileId, String userId, String delId);
    public TeamRecycle newTeamRecycle(String fileId, String teamId, String delId);

    public String getSpaceIdByTeamId(String teamId);
    public String getSpaceIdByUserId(String userId);
    public UserSpace getUserSpace(String userId);

    public ArrayList<User> getSpaceOwner(String spaceId) throws SpaceNotExist;

}
