package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Tool.Authority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpaceService {

    /**
     * @param parentId 可以为null，表示创建在根目录
     * @return FolderId
     */
    public String newFolder(String folderName, String creatorId, String parentId, String spaceId);
    /**
     * @param userId 空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     */
    public List<File> getUserSpace(String userId, String visitorId);
    /**
     * @param teamId 空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     */
    public List<File> getTeamSpace(String teamId, String visitorId);

    /**
     * @return 回收站只有空间所有者能看到，因此不需检查权限
     */
    public List<File> getUserRecycle(String userId);
    /**
     * @return 回收站只有空间所有者能看到，因此不需检查权限
     */
    public List<File> getTeamRecycle(String userId);

    /**
     * @return 需要根据权限返回Document（大于等于2，空间所有者不限权限）
     */
    public List<File> getRecentBrowser(String userId);
    /**
     * @param userId 空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     */
    public List<File> getCollection(String userId, String visitorId);
    /**
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     */
    public List<File> openFolder(String folderId, String visitorId);

    /**如果目标是文件，则要递归的修改所有子文件的now_auth
     * @param newAuth 1到4之间
     */
    public void changeAuth(String fileId, Authority newAuth);

    public Authority checkAuth(String fileId, String userId);


}
