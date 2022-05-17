package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.ZZY.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface DocumentService {

    /**
     * @param userId 创建者
     * @param authority 创建权限
     * @param fatherId 父文件夹（或空间）id
     * @return 返回Document实体，如果为null意味着创建失败
     */
    public Document newDoc(String name, String userId, int authority, String fatherId);

    /**
     * 输入用户名，获得用户的收藏文档集合
     * @param userId 非null
     * @return 收藏文档列表,为null则意味着用户不存在
     */
    public ArrayList<Document> getCollection(String userId);


//    /**
//     * 输入用户名，获得用户的收藏文档集合
//     * @param documentId 非空
//     * @return 创建者id，为null表示错误
//     */
//    public String getCreatorId(String documentId);

    /**
     * 所有参数非空
     * @param userId
     * @param documentId
     * @return 成功收藏为true
     */
    public boolean collect(String userId,String documentId);
}
