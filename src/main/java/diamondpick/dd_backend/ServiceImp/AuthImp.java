package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.AuthService;

public class AuthImp implements AuthService {
    @Override
    public void changeFileAuth(String fileId, int newAuth) throws OperationFail {

    }

    @Override
    public int checkFileAuth(String fileId, String userId) throws OperationFail {
        return 0;
    }

    @Override
    public void changeSpaceAuth(String type, String ownerId, int newAuth) throws OperationFail {

    }
}
