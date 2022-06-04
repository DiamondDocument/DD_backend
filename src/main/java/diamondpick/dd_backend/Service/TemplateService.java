package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.Template;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.NotExist.DocNotExist;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.NotExist.UserNotExist;
import diamondpick.dd_backend.Exception.OtherFail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TemplateService {

    public String newTemp(String docId, String name, String intro)throws NotExist, OtherFail;

    public void collect(String userId, String docId)throws AlreadyCollect, OtherFail;

    public void disCollect(String userId, String docId)throws NotyetCollect, OtherFail;

    //todo
    List<Template> getRecommend(String userId);

}
