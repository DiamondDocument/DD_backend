package diamondpick.dd_backend.Tool;

import diamondpick.dd_backend.Dao.*;
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

    @Autowired
    private TeamDao teamDao;
    @Autowired
    private TemplateDao templateDao;

    /**
     * 'f': folder文件夹
     * 'd': document文档
     * 'm': message消息
     */
    public String generateId(char type) throws Illegal {
        String newId;
        switch (type) {
            case 'f':
                String folderMaxId = folderDao.selectMaxId();
                int folderIdNum;
                if (folderMaxId == null) {
                    folderIdNum = 0;
                } else {
                    folderIdNum = Integer.parseInt(folderMaxId.substring(1));
                }
                newId = "f" + String.format("%06d", folderIdNum + 1);
                break;
            case 'd':
                String docMaxId = documentDao.selectMaxId();
                int documentIdNum;
                if (docMaxId == null) {
                    documentIdNum = 0;
                } else {
                    documentIdNum = Integer.parseInt(docMaxId.substring(1));
                }
                newId = "d" + String.format("%06d", documentIdNum + 1);
                break;
            case 'm':
                String msgMaxId = messageDao.selectMaxId();
                int messageIdNum;
                if (msgMaxId == null) {
                    messageIdNum = 0;
                } else {
                    messageIdNum = Integer.parseInt(msgMaxId.substring(1));
                }
                newId = "m" + String.format("%06d", messageIdNum + 1);
                break;
            case 't':
                String teamMaxId = teamDao.selectMaxId();
                int teamIdNum;
                if (teamMaxId == null) {
                    teamIdNum = 0;
                } else {
                    teamIdNum = Integer.parseInt(teamMaxId.substring(1));
                }
//                newId = "t" + String.format("%04d", teamIdNum + 1);
                newId = String.format("%05d", teamIdNum + 1);
                break;
            case 'p':
                String tempMaxId = templateDao.selectMaxId();
                int tempIdNum;
                if (tempMaxId == null) {
                    tempIdNum = 0;
                } else {
                    tempIdNum = Integer.parseInt(tempMaxId.substring(1));
                }
//                newId = "t" + String.format("%04d", teamIdNum + 1);
                newId = "t" + String.format("%06d", tempIdNum + 1);
                break;
            default:
                throw new Illegal();
        }
        return newId;
    }
}
