package diamondpick.dd_backend.yyh.Dao;

import diamondpick.dd_backend.yyh.Entity.TeamDeal;
import org.springframework.dao.DataIntegrityViolationException;


public interface TeamDealDao {


    /**
     * @param type 1 为邀请，2为申请
     */
    public void insertDeal(String teamId, String userId, int type)throws DataIntegrityViolationException;

    public void updateStatus(int dealId, int newStatus)throws DataIntegrityViolationException;

    //按时间排序的最近的
    public TeamDeal selectDeal(String teamId, String userId);


}
