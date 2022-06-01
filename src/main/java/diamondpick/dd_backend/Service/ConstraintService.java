package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.Illegal.NameIllegal;
import diamondpick.dd_backend.Exception.Illegal.PwdIllegal;
import org.springframework.stereotype.Service;

@Service
public interface ConstraintService {
    /**适用于模板、团队、用户简介*/
    public void checkIntro(String intro)throws Illegal;

    /**适用于文档、模板、用户、团队名*/
    public void checkName(String nickname)throws NameIllegal;

    public void checkPassword(String password)throws PwdIllegal;

    public void checkUserId(String userId)throws Illegal;
    public void checkEmail(String email)throws Illegal;
}
