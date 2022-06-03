package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.NotExist.DocNotExist;
import diamondpick.dd_backend.Exception.NotExist.NotExist;
import diamondpick.dd_backend.Exception.NotExist.UserNotExist;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.ConstraintService;
import diamondpick.dd_backend.Service.DocumentService;
import diamondpick.dd_backend.Service.LocalFileService;
import diamondpick.dd_backend.Tool.IdGenerator;
import org.springframework.dao.DataIntegrityViolationException;

import javax.naming.NamingException;
import java.util.List;

public class DocumentImp implements DocumentService {

    private DocumentDao documentDao;
    private ConstraintService constraintService;
    private LocalFileService localFileService;
    private AuthService authService;


    @Override
    public String newDoc(String name, String spaceId, String userId, String parentId) throws OperationFail {
        String newDocId = IdGenerator.generateId('d');
        constraintService.checkName(name);
        try{
            documentDao.insertDoc(newDocId, name, userId, parentId, spaceId);
            localFileService.saveDocument(newDocId, "");
            return newDocId;
        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFail();
        }
    }

    @Override
    public String newDocByTemplate(String name, String spaceId, String userId, String parentId, String tempId) throws OperationFail {
        String newDocId = IdGenerator.generateId('d');
        constraintService.checkName(name);
        try{
            documentDao.insertDoc(newDocId, name, userId, parentId, spaceId);
            localFileService.saveDocument(newDocId, localFileService.getTemplate(tempId));
            return newDocId;
        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFail();
        }
    }

    @Override
    public String getSize(String docId) throws OperationFail {
        return localFileService.getDocSize(docId);
    }

    @Override
    public void collect(String userId, String docId) throws  NoAuth, AlreadyCollect, OtherFail {
        if(documentDao.selectCollectorAndDoc(userId, docId) != null)throw new AlreadyCollect();
        try{
            if(authService.checkFileAuth(docId, userId) < 2)throw new NoAuth();
        }catch (OperationFail e){
            throw new NoAuth();
        }
        try{
            documentDao.insertCollection(userId, docId);
        }catch (DataIntegrityViolationException e){
            throw new OtherFail();
        }
    }

    @Override
    public void disCollect(String userId, String docId) throws  NotyetCollect {
        if(documentDao.selectCollectorAndDoc(userId, docId) == null)throw new NotyetCollect();
        documentDao.deleteCollection(userId, docId);
    }

    @Override
    public int checkShare(String docId) throws OperationFail {
        //todo
        return 1;
    }
}
