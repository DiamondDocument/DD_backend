package diamondpick.dd_backend.Service;

import diamondpick.dd_backend.Entity.lyz.*;
import diamondpick.dd_backend.Entity.yyh.User;
import diamondpick.dd_backend.Exception.NotExist.*;
import diamondpick.dd_backend.zzy.Entity.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface SpaceService {
    //返回新空间的id
    public String newSpace();

    public List<Document> getRootDocumentInSpace();
    public List<Folder> getRootFolderInSpace();



    public UserSpace newUserSpace(String userId, String userSpaceName);
    public TeamSpace newTeamSpace(String teamId, String teamSpaceName);
    public UserRecycle newUserRecycle(String fileId, String userId, String delId);
    public TeamRecycle newTeamRecycle(String fileId, String teamId, String delId);

    public String getSpaceIdByTeamId(String teamId);
    public String getSpaceIdByUserId(String userId);
    public UserSpace getUserSpace(String userId);

    public ArrayList<User> getSpaceOwner(String spaceId) throws SpaceNotExist;

}
