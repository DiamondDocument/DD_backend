package diamondpick.dd_backend.Service.lyz;

import diamondpick.dd_backend.Dao.SpaceDao;
import diamondpick.dd_backend.Entity.lyz.Space;
import diamondpick.dd_backend.Service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SpaceServiceImpl implements SpaceService {
    @Autowired
    private SpaceDao spaceDao;

    @Override
    public Space newUserSpace(String userId) {
        return null;
    }

    @Override
    public Space newTeamSpace(String teamId) {
        return null;
    }

    @Override
    public Space newUserRecycle(String userId) {
        return null;
    }

    @Override
    public Space newTeamRecycle(String teamId) {
        return null;
    }

    @Override
    public ArrayList<Space> getMySpace(String userId) {
        return spaceDao.getMySpace(userId);
    }

    @Override
    public ArrayList<Space> getTeamSpace(String teamId) {
        return spaceDao.getTeamSpace(teamId);
    }

    @Override
    public ArrayList<Space> getCollectionSpace(String userId) {
        return spaceDao.getCollectionSpace(userId);
    }

    @Override
    public ArrayList<Space> getRecycleSpace(String userId) {
        return spaceDao.getRecycleSpace(userId);
    }

    @Override
    public ArrayList<Space> getLastSpace(String userId) {
        return spaceDao.getLastSpace(userId);
    }
}
