package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.TeamDeal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataIntegrityViolationException;

@Mapper
public interface TeamDealDao {


    /**
     * @param type 1 为邀请，2为申请
     */
    @Insert("insert into team_deal(team_id,user_id,deal_type) values(#{param1}, #{param2}, #{param3})")
    public void insertDeal(String teamId, String userId, int type)throws DataIntegrityViolationException;
    @Update("update team_deal set deal_status = #{param2} where deal_id = #{param1}")
    public void updateStatus(int dealId, int newStatus)throws DataIntegrityViolationException;

    //todo 需要让TeamDeal返回对应的团队名和用户名（通过join等语句）
    @Select("select team_deal.*,nickname,name from team_deal,teams,users where team_deal.team_id = #{param1} and team_deal.user_id = #{param2} and users.user_id = #{param2} and teams.team_id = #{param1} order by create_time desc limit 1")
    public TeamDeal selectDeal(String teamId, String userId);


}
