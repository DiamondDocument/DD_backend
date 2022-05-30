package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.TeamDeal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataIntegrityViolationException;


public interface TeamDealDao {


    /*
     * @param type 1 为邀请，2为申请
     */
    @Insert("insert into team_deal values(#{param1}, #{param2},#{param3}")
    public void insertDeal(String teamId, String userId, int type)throws DataIntegrityViolationException;
    @Update("update team_deal set deal_status = #{param2} where deal_id = #{param1}")
    public void updateStatus(int dealId, int newStatus)throws DataIntegrityViolationException;

    //按时间排序的最近的
    @Select("select * from team_deal where team_id = #{param1} and user_id = #{param2} order by create_time desc limit 1")
    public TeamDeal selectDeal(String teamId, String userId);


}
