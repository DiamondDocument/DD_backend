package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.Illegal.Illegal;
import org.springframework.stereotype.Service;

@Service
public interface ConstraintService {
    /**适用于模板、团队、用户简介*/
    public void checkIntro(String intro)throws Illegal;

    /**适用于文档、模板、用户、团队名*/
    public void checkName(String nickname)throws Illegal;

    public void checkPassword(String password)throws Illegal;

    public void checkUserId(String userId)throws Illegal;
    public void checkEmail(String email)throws Illegal;
}
