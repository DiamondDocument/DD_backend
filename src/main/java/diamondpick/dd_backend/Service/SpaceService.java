package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OperationFail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpaceService {


    /**
     * @param userId 空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     */
    public List<File> getUserSpace(String userId, String visitorId)throws OperationFail;
    /**
     * @param teamId 空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     */
    public List<File> getTeamSpace(String teamId, String visitorId)throws OperationFail;

    /**
     * @return 回收站只有空间所有者能看到，因此不需检查权限
     */
    public List<File> getUserRecycle(String userId)throws OperationFail;
    /**
     * @return 回收站只有空间所有者能看到，因此不需检查权限
     */
    public List<File> getTeamRecycle(String userId)throws OperationFail;

    /**
     * @return 需要根据权限返回Document（大于等于2，空间所有者不限权限）
     */
    public List<File> getRecentBrowser(String userId)throws OperationFail;
    /**
     * @param userId 空间所属者
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     */
    public List<File> getCollection(String userId, String visitorId)throws OperationFail;


}
