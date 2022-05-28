package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.NotExist.DocNotExist;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.NotExist.UserNotExist;
import diamondpick.dd_backend.Exception.OtherFail;
import org.springframework.stereotype.Service;

@Service
public interface TemplateService {

    public String newTemp(String docId)throws NotExist, OtherFail;

    public void collect(String userId, String docId)throws UserNotExist, DocNotExist, AlreadyCollect;

    public void disCollect(String userId, String docId)throws UserNotExist, DocNotExist, NotyetCollect;

}
