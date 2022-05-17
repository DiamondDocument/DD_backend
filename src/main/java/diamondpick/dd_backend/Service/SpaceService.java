package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.Space;
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
    public Space newUserSpace(String userId);
    public Space newTeamSpace(String teamId);
    public Space newUserRecycle(String userId);
    public Space newTeamRecycle(String teamId);


    public ArrayList<Space> getMySpace(String userId);
    public ArrayList<Space> getTeamSpace(String teamId);
    public ArrayList<Space> getCollectionSpace(String userId);
    public ArrayList<Space> getRecycleSpace(String userId);
    public ArrayList<Space> getLastSpace(String userId);
}
