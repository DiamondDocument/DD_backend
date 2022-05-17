package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.Space;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface SpaceService {
    public ArrayList<Space> getMySpace(String userId);
    public ArrayList<Space> getTeamSpace(String teamId);
    public ArrayList<Space> getCollectionSpace(String userId);
    public ArrayList<Space> getRecycleSpace(String userId);
    public ArrayList<Space> getLastSpace(String userId);
}
