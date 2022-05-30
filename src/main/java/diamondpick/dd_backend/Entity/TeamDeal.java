package diamondpick.dd_backend.Entity;

import java.util.Date;

public class TeamDeal {
    //除了TeamDeal表外还要求包含用户名和团队名
    int dealId;
    String teamId;
    String teamName;
    String userId;
    String userName;
    int dealType;
    Date createTime;
    int dealStatus;

}
