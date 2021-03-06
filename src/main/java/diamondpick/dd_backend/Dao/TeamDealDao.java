package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.TeamDeal;
import org.apache.ibatis.annotations.*;
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

    @Select("select team_deal.*,nickname,name from team_deal,teams,users where team_deal.team_id = #{param1} and team_deal.user_id = #{param2} and users.user_id = #{param2} and teams.team_id = #{param1} order by create_time desc limit 1")
    public TeamDeal selectDeal(String teamId, String userId);

    @Select("select * from team_deal where deal_id = #{param1}")
    public TeamDeal selectDealByDealId(int dealId);

    @Delete("delete from team_deal where team_id = #{param1}")
    public void deleteAllDeal(String teamId);
}
