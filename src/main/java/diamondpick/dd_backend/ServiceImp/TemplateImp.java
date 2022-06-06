package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.TemplateDao;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.Template;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Service.ConstraintService;
import diamondpick.dd_backend.Service.LocalFileService;
import diamondpick.dd_backend.Service.TemplateService;
import diamondpick.dd_backend.Tool.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateImp implements TemplateService {
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private ConstraintService constraintService;
    @Autowired
    private LocalFileService localFileService;
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private IdGenerator idGenerator;
    @Override
    public String newTemp(String docId, String name, String intro) throws OperationFail {
        constraintService.checkName(name);
        constraintService.checkIntro(intro);
        Document doc = documentDao.selectDoc(docId);
        String newId = 't' + doc.getDocId().substring(1);
        templateDao.insertTemp(newId, name, doc.getCreatorId(),intro);
        localFileService.saveTemplate(newId, localFileService.getDocument(docId));
        return newId;
    }

    @Override
    public void collect(String userId, String tempId) throws AlreadyCollect, OtherFail {
        if(templateDao.selectCollectorAndTemp(userId, tempId) != null)throw new AlreadyCollect();
        try{
            templateDao.insertCollection(userId, tempId);
        }catch (Exception e){
            e.printStackTrace();
            throw new OtherFail();
        }
    }

    @Override
    public void disCollect(String userId, String tempId) throws NotyetCollect, OtherFail {
        if(templateDao.selectCollectorAndTemp(userId, tempId) == null)throw new NotyetCollect();
        try{
            templateDao.deleteCollection(userId, tempId);
        }catch (Exception e){
            e.printStackTrace();
            throw new OtherFail();
        }
    }

    @Override
    public List<Template> getRecommend(String userId) throws OperationFail {
        return null;
    }

    @Override
    public List<Template> getMyTemplate(String userId) throws OperationFail {
        try{
            return templateDao.selectByCreator(userId);
        }catch (Exception e){
            e.printStackTrace();
            throw new OtherFail();
        }
    }

    @Override
    public List<Template> getCollection(String userId) throws OperationFail {
        try{
            return templateDao.selectCollection(userId);
        }catch (Exception e){
            e.printStackTrace();
            throw new OtherFail();
        }
    }


}
