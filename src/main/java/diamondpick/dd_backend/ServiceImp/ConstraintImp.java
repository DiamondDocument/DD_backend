package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Exception.Illegal.Illegal;
import diamondpick.dd_backend.Exception.Illegal.NameIllegal;
import diamondpick.dd_backend.Exception.Illegal.PwdIllegal;
import diamondpick.dd_backend.Service.ConstraintService;
import org.springframework.stereotype.Service;

@Service
public class ConstraintImp implements ConstraintService {
//todo
    @Override
    public void checkIntro(String intro) throws Illegal {

    }

    @Override
    public void checkName(String nickname) throws NameIllegal {

    }

    @Override
    public void checkPassword(String password) throws PwdIllegal {

    }

    @Override
    public void checkUserId(String userId) throws Illegal {

    }

    @Override
    public void checkEmail(String email) throws Illegal {

    }
}
