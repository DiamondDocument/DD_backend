package diamondpick.dd_backend.Service.lyz;

import diamondpick.dd_backend.Dao.lyz.SpaceDao;
import diamondpick.dd_backend.Entity.lyz.TeamRecycle;
import diamondpick.dd_backend.Entity.lyz.TeamSpace;
import diamondpick.dd_backend.Entity.lyz.UserRecycle;
import diamondpick.dd_backend.Entity.lyz.UserSpace;
import diamondpick.dd_backend.Service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceServiceImpl implements SpaceService {
    @Autowired
    private SpaceDao spaceDao;

    @Override
    public UserSpace newUserSpace(String userId) {
        return null;
    }

    @Override
    public TeamSpace newTeamSpace(String teamId) {
        return null;
    }

    @Override
    public UserRecycle newUserRecycle(String userId) {
        return null;
    }

    @Override
    public TeamRecycle newTeamRecycle(String teamId) {
        return null;
    }
}
