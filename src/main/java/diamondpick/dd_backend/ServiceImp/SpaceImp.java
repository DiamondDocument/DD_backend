package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.SpaceService;

import java.util.List;

public class SpaceImp implements SpaceService {
    @Override
    public List<File> getUserSpace(String userId, String visitorId) throws OperationFail {
        return null;
    }

    @Override
    public List<File> getTeamSpace(String teamId, String visitorId) throws OperationFail {
        return null;
    }

    @Override
    public List<File> getUserRecycle(String userId) throws OperationFail {
        return null;
    }

    @Override
    public List<File> getTeamRecycle(String teamId) throws OperationFail {
        return null;
    }

    @Override
    public List<File> getRecentBrowser(String userId) throws OperationFail {
        return null;
    }

    @Override
    public List<File> getCollection(String userId, String visitorId) throws OperationFail {
        return null;
    }
}
