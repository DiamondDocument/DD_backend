package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Entity.File;
import diamondpick.dd_backend.Exception.NoAuth;
import diamondpick.dd_backend.Exception.OtherFail;
import diamondpick.dd_backend.Service.FileService;

import java.util.List;

public class FileImp implements FileService {
    @Override
    public String newFolder(String folderName, String creatorId, String parentId, String spaceId) throws OtherFail, NoAuth {
        return null;
    }

    @Override
    public List<File> openFolder(String folderId, String visitorId) throws OtherFail, NoAuth {
        return null;
    }

    @Override
    public void moveFile(String newParentId) throws OtherFail, NoAuth {

    }

    @Override
    public void deleteFile(String fileId) throws OtherFail, NoAuth {

    }

    @Override
    public void DeletePermanently(String fileId) throws OtherFail, NoAuth {

    }
}
