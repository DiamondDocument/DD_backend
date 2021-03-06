package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OtherFail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileService {
    /**
     * 新建文件夹
     *
     * @param folderName 文件夹名称
     * @param creatorId  创建者id
     * @param parentId   父文件夹id
     * @param spaceId    空间id
     * @return FolderId 新建的文件夹id
     * @throws OtherFail 其他错误
     * @throws NoAuth    没有权限
     */
    public String newFolder(String folderName, String creatorId, String parentId, String spaceId) throws OtherFail, NoAuth;

    /**
     * 打开文件夹
     *
     * @param folderId  文件夹id
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     * @throws OtherFail 其他错误
     * @throws NoAuth    没有权限
     */
    public List<File> openFolder(String folderId, String visitorId) throws OtherFail, NoAuth;

    /**
     * 移动文件夹
     *
     * @param fileId      文件id
     * @param newParentId 如果为空，则意为移至根目录
     * @throws OtherFail 其他错误
     * @throws NoAuth    没有权限
     */
    void moveFile(String fileId, String newParentId) throws OtherFail, NoAuth;

    /**
     * 删除文件
     * 只需要将fileId代表的文件标记为delete就行了
     *
     * @param fileId 文件id（文档或文件夹）
     * @param userId 删除者id
     * @return 删除状态：0-成功，-1-失败，1-没有权限
     * @throws OtherFail 其他错误
     * @throws NoAuth    没有权限
     */
    public int deleteFile(String fileId, String userId) throws OtherFail, NoAuth;

    /**
     * 恢复文件
     *
     * @param fileId 文件id
     * @throws OtherFail 其他错误
     * @throws NoAuth    没有权限
     */
    public void recoverFile(String fileId) throws OtherFail, NoAuth;

    /**
     * 永久删除文件
     * 如果是文件夹需要递归删除所有文件
     *
     * @param fileId 文件id（文档或文件夹）
     * @param userId 删除者id
     * @return 删除状态：0-成功，-1-失败，1-没有权限
     * @throws OtherFail 其他错误
     * @throws NoAuth    没有权限
     */
    public int deletePermanently(String fileId, String userId) throws OtherFail, NoAuth;

    /**
     * 递归修改文件权限
     *
     * @param parentId 父文件夹id
     * @param newAuth  新权限
     */
    public void updateAuthRecur(String parentId, int newAuth);
}
