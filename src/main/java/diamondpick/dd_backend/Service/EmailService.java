package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.OperationFail;
import org.springframework.stereotype.Service;

//todo
@Service
public interface EmailService {

    public void sendVerification(String email)throws OperationFail;
    public void checkVerification(String email, String code)throws OperationFail;
}
