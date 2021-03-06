package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.UserDao;
import diamondpick.dd_backend.Entity.Comment;
import diamondpick.dd_backend.Entity.User;
import diamondpick.dd_backend.Exception.Document.AlreadyCollect;
import diamondpick.dd_backend.Exception.Document.CannotEdit;
import diamondpick.dd_backend.Exception.Document.NotyetCollect;
import diamondpick.dd_backend.Exception.Document.SomoneEditing;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Service.*;
import diamondpick.dd_backend.Tool.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class DocumentImp implements DocumentService {

    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private ConstraintService constraintService;
    @Autowired
    private LocalFileService localFileService;
    @Autowired
    private AuthService authService;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserDao userDao;

    @Override
    public String newDoc(String name, String spaceId, String userId, String parentId, MultipartFile file) throws OperationFail {
        String newDocId = idGenerator.generateId('d');
        constraintService.checkName(name);
        try{
            documentDao.insertDoc(newDocId, name, userId, parentId, spaceId);
            if(file != null){
                localFileService.saveDocument(newDocId, localFileService.docxToHtml(file));
            }else{
                localFileService.saveDocument(newDocId, "");
            }
            return newDocId;
        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFail();
        }
    }

    @Override
    public String newDocByTemplate(String name, String spaceId, String userId, String parentId, String tempId) throws OperationFail {
        String newDocId = idGenerator.generateId('d');
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

    private Map<String, Integer> shareMap = new HashMap<>();
    private Map<String, Date> editingMapByDate = new HashMap<>();
    private Map<String, String> editingMapByEditor = new HashMap<>();

    @Override
    public int checkShare(String docId) {
        Integer ret = shareMap.get(docId);
        if(ret == null)return 1;
        return ret;
    }

    @Override
    public void keepEdit(String userId, String docId) throws NoAuth, SomoneEditing, OtherFail {
        try{
            if(documentDao.selectDoc(docId) == null)throw new OtherFail();
            if(authService.checkFileAuth(docId, userId) < 4)throw new NoAuth();
            String editor = editingMapByEditor.get(docId);
            if(editor != null && !editor.equals(userId) &&
                    new Date().getTime() - editingMapByDate.get(docId).getTime() < 21000 )throw new SomoneEditing();
            editingMapByEditor.put(docId, userId);
            editingMapByDate.put(docId, new Date());
        }catch (NoAuth e){
            throw new NoAuth();
        }catch (SomoneEditing e){
            throw new SomoneEditing();
        }catch (OperationFail e ){
            throw new OtherFail();
        }
    }

    @Override
    public void quitEdit(String userId, String docId) throws OperationFail {
        editingMapByEditor.remove(docId);
        editingMapByDate.remove(docId);
    }

    @Override
    public void share(String docId, int auth)throws OperationFail {
        if(auth > 4 || auth < 2)throw new OperationFail();
        if(documentDao.selectDoc(docId) == null)throw new OperationFail();
        shareMap.put(docId, auth);
    }

    @Override
    public void disShare(String docId) throws OperationFail {
        shareMap.remove(docId);
    }

    @Override
    public List<Comment> getComment(String docId) throws OperationFail {
        try{
            return documentDao.selectCommentByDoc(docId);
        }catch (Exception e){
            throw new OperationFail();
        }
    }

    @Override
    public void newComment(String content, String docId, String creatorId) throws NoAuth, OtherFail {
        try{
            if(authService.checkFileAuth(docId, creatorId) < 3)throw new NoAuth();
            documentDao.insertComment(content, creatorId, docId);
            String docCreator = documentDao.selectDoc(docId).getCreatorId();
            if(!creatorId.equals(docCreator)){
                messageService.newCommentMsg(creatorId, docId, docCreator, content);
            }
            String[] ats = content.split("@");
            for(int i = 1; i < ats.length; i++){
                String at = ats[i];
                at = at.split(" ")[0];
                User user = userDao.selectUser(at);
                if(user != null && !user.getUserId().equals(docCreator)){
                    messageService.newAtMsg(creatorId, docId, user.getUserId());
                }
            }
        }catch (Exception e){
            throw new OtherFail();
        }
    }

    @Override
    public void deleteComment(int commentId, String userId) throws NoAuth, OtherFail {
        try{
            Comment cmt = documentDao.selectComment(commentId);
            String commentCreator = cmt.getCreatorId();
            String docCreator = documentDao.selectDoc(cmt.getDocId()).getCreatorId();
            if(!(commentCreator.equals(userId) || docCreator.equals(userId)))throw new NoAuth();
            documentDao.deleteComment(commentId);
        }catch (NoAuth e){
            throw new NoAuth();
        } catch (Exception e){
            e.printStackTrace();
            throw new OtherFail();
        }
    }

    @Override
    public void checkEdit(String docId, String userId) throws CannotEdit {
        try{
            if(editingMapByEditor.get(docId).equals(userId) &&
                    new Date().getTime() - editingMapByDate.get(docId).getTime() < 21000 )return;
            else throw new CannotEdit();
        }catch (Exception e){
            throw new CannotEdit();
        }

    }
}
