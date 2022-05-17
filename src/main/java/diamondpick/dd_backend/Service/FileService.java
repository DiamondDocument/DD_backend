package diamondpick.dd_backend.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    /**
     * 会覆盖之前已经存在的文件
     * @param directory
     * @param file
     * @return 保存错误会返回false
     */
    public boolean saveFile(String directory, MultipartFile file);

    /**
     *
     * @param path:文件路径
     * @return 返回空表示错误
     */
    public byte[] getFile(String path);
}
