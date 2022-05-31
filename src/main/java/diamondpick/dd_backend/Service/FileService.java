package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileService {

    /**
     * @param parentId 可以为null，表示创建在根目录
     * @return FolderId
     */
    public String newFolder(String folderName, String creatorId, String parentId, String spaceId)throws OtherFail,NoAuth;
    /**
     * @param visitorId 访客id（也可能是本人）
     * @return 需要根据权限返回File（大于等于2,空间所有者不限权限）
     */
    public List<File> openFolder(String folderId, String visitorId)throws OtherFail, NoAuth;

    /**@param newParentId 如果为空，则意为移至根目录*/
    public void moveFile(String newParentId)throws OtherFail, NoAuth;

    /**只需要将fileId代表的文件标记为delete就行了*/
    public void deleteFile(String fileId)throws OtherFail, NoAuth;

    /**如果是文件夹需要递归删除所有文件*/
    public void DeletePermanently(String fileId)throws OtherFail, NoAuth;
}
