package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Exception.OperationFail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpaceService {
    /**
     * 获取用户空间文件列表
     *
     * @param userId    空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     * @throws OperationFail 操作失败
     */
    public List<File> getUserSpace(String userId, String visitorId) throws OperationFail;

    /**
     * 获取团队空间文件列表
     *
     * @param teamId    空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     * @throws OperationFail 操作失败
     */
    public List<File> getTeamSpace(String teamId, String visitorId) throws OperationFail;

    /**
     * 获取用户回收站文件列表
     *
     * @param userId 空间所属者
     * @return 回收站只有空间所有者能看到，因此不需检查权限
     * @throws OperationFail 操作失败
     */
    public List<File> getUserRecycle(String userId) throws OperationFail;

    /**
     * 获取团队回收站文件列表
     *
     * @param teamId 空间所属者
     * @return 回收站只有空间所有者能看到，因此不需检查权限
     * @throws OperationFail 操作失败
     */
    public List<File> getTeamRecycle(String teamId) throws OperationFail;

    /**
     * 获取最近浏览文件列表
     *
     * @param userId 用户id
     * @return 需要根据权限返回Document（大于等于2，空间所有者不限权限）
     * @throws OperationFail 操作失败
     */
    public List<File> getRecentBrowser(String userId) throws OperationFail;

    /**
     * 获取收藏夹文件列表
     *
     * @param userId    空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     * @throws OperationFail 操作失败
     */
    public List<File> getCollection(String userId, String visitorId) throws OperationFail;
}
