package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.TeamRecycle;
import diamondpick.dd_backend.Entity.lyz.TeamSpace;
import diamondpick.dd_backend.Entity.lyz.UserRecycle;
import diamondpick.dd_backend.Entity.lyz.UserSpace;
import org.springframework.stereotype.Service;

@Service
public interface SpaceService {

     /*
    周五之前实现
    周五之前实现
    周五之前实现
     */
    //////////////
    //返回类型改成对应的
    public UserSpace newUserSpace(String userId);
    public TeamSpace newTeamSpace(String teamId);
    public UserRecycle newUserRecycle(String userId);
    public TeamRecycle newTeamRecycle(String teamId);

}
