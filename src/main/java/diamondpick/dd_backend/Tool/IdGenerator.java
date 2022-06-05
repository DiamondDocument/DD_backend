package diamondpick.dd_backend.Tool;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.FolderDao;
import diamondpick.dd_backend.Dao.MessageDao;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdGenerator {
    @Autowired
    private FolderDao folderDao;

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private MessageDao messageDao;

    /**
     * 'f': folder文件夹
     * 'd': document文档
     * 'm': message消息
     */
    public String generateId(char type) throws Illegal{
        String newId;
        switch (type) {
            case 'f':
                int folderIdNum = Integer.parseInt(folderDao.selectMaxId().substring(1)) + 1;
                newId = "f" + String.format("%06d", folderIdNum);
                break;
            case 'd':
                int documentIdNum = Integer.parseInt(documentDao.selectMaxId().substring(1)) + 1;
                newId = "d" + String.format("%06d", documentIdNum);
                break;
            case 'm':
                int messageIdNum = Integer.parseInt(messageDao.selectMaxId().substring(1)) + 1;
                newId = "m" + String.format("%06d", messageIdNum);
                break;
            default:
                throw new Illegal();
        }
        return newId;
    }
}
