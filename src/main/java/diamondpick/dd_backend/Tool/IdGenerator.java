package diamondpick.dd_backend.Tool;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.FolderDao;
import diamondpick.dd_backend.Dao.MessageDao;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import org.springframework.beans.factory.annotation.Autowired;

public class IdGenerator {
    /**
     * 'f': folder文件夹
     * 'd': document文档
     * 'm': message消息
     */
    public char idType;
    public int idNum;

    @Autowired
    private FolderDao folderDao;

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private MessageDao messageDao;

    public String getId(String type) throws Illegal {
        String newId;
        switch (type) {
            case "folder":
                idType = 'f';
                idNum = Integer.parseInt(folderDao.selectMaxId().substring(1)) + 1;
                newId = idType + String.format("%06d", idNum);
                break;
            case "document":
                idType = 'd';
                idNum = Integer.parseInt(documentDao.selectMaxId().substring(1)) + 1;
                newId = idType + String.format("%06d", idNum);
                break;
            case "message":
                idType = 'm';
                idNum = Integer.parseInt(messageDao.selectMaxId().substring(1)) + 1;
                newId = idType + String.format("%06d", idNum);
                break;
            default:
                throw new Illegal();
        }
        return newId;
    }
}
