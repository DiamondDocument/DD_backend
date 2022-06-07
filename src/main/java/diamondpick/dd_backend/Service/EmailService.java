package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.OperationFail;
import org.springframework.stereotype.Service;

//todo
@Service
public interface EmailService {
    public void sendSimpleMail(String to, String subject, String contnet);
    public void sendVerification(String email);
    public void checkVerification(String email, String code)throws OperationFail;
}
