package diamondpick.dd_backend.Tool;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.FolderDao;
import diamondpick.dd_backend.Dao.MessageDao;
import diamondpick.dd_backend.Exception.Illegal.Illegal;
import org.springframework.beans.factory.annotation.Autowired;

public class IdGenerator {
    @Autowired
    private static FolderDao folderDao;

    @Autowired
    private static DocumentDao documentDao;

    @Autowired
    private static MessageDao messageDao;

    /**
     * 'f': folder文件夹
     * 'd': document文档
     * 'm': message消息
     */
    private char idType;
    private String newId;
    private static int folderIdNum = Integer.parseInt(folderDao.selectMaxId().substring(1));
    private static int documentIdNum = Integer.parseInt(documentDao.selectMaxId().substring(1));
    private static int messageIdNum = Integer.parseInt(messageDao.selectMaxId().substring(1));

    public IdGenerator(char idType) throws Illegal {
        this.idType = idType;
        switch (idType) {
            case 'f':
                newId = "f" + String.format("%06d", folderIdNum++);
                break;
            case 'd':
                newId = "d" + String.format("%06d", documentIdNum++);
                break;
            case 'm':
                newId = "m" + String.format("%06d", messageIdNum++);
                break;
            default:
                throw new Illegal();
        }
    }

    public String getNewId() {
        return newId;
    }

    //又加了一个静态方法，便利书写
    public static String generateId(char type) throws Illegal{
        return new IdGenerator(type).getNewId();
    }
}
