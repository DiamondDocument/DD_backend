package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.zzy.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface DocumentService {

    /**
     * @param userId 创建者
     * @param authority 创建权限
     * @param parentId 父文件夹（或空间）id
     * @return 返回Document实体，如果为null意味着创建失败
     */
    public String newDoc(String name, String spaceId, String userId, int authority, String parentId);

    /**
     * 输入用户名，获得用户的收藏文档集合
     * @param userId 非null
     * @return 收藏文档列表,为null则意味着用户不存在
     */
    public ArrayList<Document> getCollection(String userId);

    /**
     * @return 返回一个形式化的字符串，如"98K"或者"1.2M"
     */
    public String getSize(String docId);

    /**
     * 所有参数非空
     * @param userId
     * @param docId
     * @return 成功收藏为true
     */
    public int collect(String userId, String docId);

    public int discollect(String userId, String docId);

}
