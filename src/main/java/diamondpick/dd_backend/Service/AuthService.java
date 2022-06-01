package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.OperationFail;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    /**
     * 更改文件权限
     * 如果目标是文件夹，则要递归的修改所有子文件的now_auth
     *
     * @param fileId  文件id（文档或文件夹）
     * @param newAuth 1到4之间
     * @throws OperationFail 操作失败
     */
    public void changeFileAuth(String fileId, int newAuth) throws OperationFail;

    /**
     * 查询文件夹权限
     *
     * @param fileId 文件id（文档或文件夹）
     * @param userId 用户id
     * @return 返回该文件在该用户视角下的权限，如果是5则代表具有所有权限
     * @throws OperationFail 操作失败
     */
    public int checkFileAuth(String fileId, String userId) throws OperationFail;

    /**
     * 更改空间权限
     *
     * @param type    "user"或者"team"
     * @param ownerId 空间拥有者id
     * @param newAuth 1-4之间
     * @throws OperationFail 操作失败
     */
    public void changeSpaceAuth(String type, String ownerId, int newAuth) throws OperationFail;
}
