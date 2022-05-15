package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.User;

import javax.mail.MessagingException;
import java.util.ArrayList;

public interface DocumentDao {
    /**
     * 输入用户名，获得用户的收藏文档集合
     * @param uId 非null
     * @return 收藏文档列表
     */
    ArrayList<Document> getCollectionDocument(String uId);

    User getUserByDocumentId(String dId);
    /**
     * 在表中新建一个文档
     * @return 新建文档的id
     */
    String NewDocument();
}
