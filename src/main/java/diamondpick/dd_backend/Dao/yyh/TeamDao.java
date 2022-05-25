package diamondpick.dd_backend.Dao.yyh;

import diamondpick.dd_backend.Entity.yyh.TeamMember;
import diamondpick.dd_backend.Entity.yyh.Team;
import diamondpick.dd_backend.Entity.yyh.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TeamDao {




    Team selectTeam(String teamId);
    /*
    key是字段，value是值
    用这个查找队长所在团队
    注意该方法如果查找结果为空，返回的是长度为0的List
     */
    List<Team> selectTeamBy(String key, String value);
    List<User> selectMember(String teamId);
    User selectCaption(String teamId);
    List<Team> selectTeamByMember(String memberId);

    /*
    insert相关方法如果插入失败会抛出异常，可以通过捕获异常来判断是否插入成功
     */
    void insertTeam(String teamId, String name, String intro, String captainId);
    void insertMember(String teamId, String memberId);

    /*
       key是字段，value是值，更新也可能会抛出异常
    */
    public void updateTeam(String teamId, String key, String value);

    /*
    删除不会抛出异常
     */
    public void deleteTeam(String teamId);
    public void deleteTeamMember(String teamId, String memberId);


}
