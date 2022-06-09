package diamondpick.dd_backend.ServiceImp;

import diamondpick.dd_backend.Dao.DocumentDao;
import diamondpick.dd_backend.Dao.FolderDao;
import diamondpick.dd_backend.Dao.TeamDao;
import diamondpick.dd_backend.Entity.Document;
import diamondpick.dd_backend.Entity.Folder;
import diamondpick.dd_backend.Entity.Team;
import diamondpick.dd_backend.Exception.OperationFail;
import diamondpick.dd_backend.Service.AuthService;
import diamondpick.dd_backend.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuthImp implements AuthService {
    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private FolderDao folderDao;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private TeamDao teamDao;

    @Override
    public void changeFileAuth(String fileId, int newAuth) throws OperationFail {
        try {
            if (fileId.charAt(0) == 'f') {
                folderDao.updateSubDirAuth(fileId, newAuth);
            } else if (fileId.charAt(0) == 'd') {
                documentDao.updateDoc(fileId, "now_auth", newAuth);
            } else {
                throw new OperationFail();
            }
        } catch (Exception e) {
            throw new OperationFail();
        }
    }

    @Override
    public int checkFileAuth(String fileId, String userId) throws OperationFail {
        try {
            if (fileId.charAt(0) == 'f') {
                Folder folder = folderDao.selectFolder(fileId);
                int spaceId = Integer.parseInt(folder.getSpaceId());
                Team teamFile = teamDao.selectTeamBySpace(spaceId);
                List<Team> teamUser = teamDao.selectTeamByMember(userId);
                boolean inTeam = false;
                for (Team team : teamUser) {
                    if (teamFile != null && Objects.equals(team.getTeamId(), teamFile.getTeamId())) {
                        inTeam = true;
                        break;
                    }
                }
                if (folder.getCreatorId().equals(userId)) {
                    return 5;
                } else if (inTeam) {
                    return 5;
                } else {
                    return folder.getNowAuth();
                }
            } else if (fileId.charAt(0) == 'd') {
                Document document = documentDao.selectDoc(fileId);
                int spaceId = Integer.parseInt(document.getSpaceId());
                Team teamFile = teamDao.selectTeamBySpace(spaceId);
                List<Team> teamUser = teamDao.selectTeamByMember(userId);
                boolean inTeam = false;
                for (Team team : teamUser) {
                    if (teamFile != null && Objects.equals(team.getTeamId(), teamFile.getTeamId())) {
                        inTeam = true;
                        break;
                    }
                }
                if (document.getCreatorId().equals(userId)) {
                    return 5;
                } else if (inTeam) {
                    return 5;
                } else {
                    return Math.max(documentService.checkShare(fileId), document.getNowAuth());
                }
            } else {
                throw new OperationFail();
            }
        } catch (Exception e) {
            throw new OperationFail();
        }
    }

    @Override
    public void changeSpaceAuth(String type, String ownerId, int newAuth) throws OperationFail {
        try {
            folderDao.updateRootDirAuth(type, ownerId, newAuth);
        } catch (Exception e) {
            throw new OperationFail();
        }
    }
}
