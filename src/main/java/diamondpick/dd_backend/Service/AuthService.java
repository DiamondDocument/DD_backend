package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Exception.OtherFail;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    /**如果目标是文件夹，则要递归的修改所有子文件的now_auth
     * @param newAuth 1到4之间
     */
    public void changeFileAuth(String fileId, int newAuth)throws OperationFail;

    /**
     * @return 如果是5则代表具有所有权限
     */
    public int checkFileAuth(String fileId, String userId)throws OperationFail;

    /**
     * @param type "user"或者"team"
     * @param ownerId 空间拥有者id
     * @param newAuth 1-4之间
     */
    public void changeSpaceAuth(String type, String ownerId, int newAuth)throws OperationFail;

}
