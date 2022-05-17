package diamondpick.dd_backend.Entity.lyz;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Folder {
    @Id
    private String folderId;
    private String folderName;
    private String creatorId;
    private Date createTime;
    private String parentId;

    // 文件生成时的权限
    private int selfAuthority;

    // 文件结合其父文件生成的权限
    private int nowAuthority;

    // 项目启动时查询数据库表中已有的文件夹数量
    private static int folderIdCounter = 0;

    public Folder() {
    }

    public Folder(String folderName, String creatorId, Date createTime, String parentId, int selfAuthority, int nowAuthority) {
        String id = "f" + String.format("%06d", folderIdCounter);
        folderIdCounter++;
        this.folderId = id;
        this.folderName = folderName;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.parentId = parentId;
        this.selfAuthority = selfAuthority;
        this.nowAuthority = nowAuthority;
    }

    public String getFolderId() {
        return folderId;
    }

    public String getFolderName() {
        return folderName;
    }
}
