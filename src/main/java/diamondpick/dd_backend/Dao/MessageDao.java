package diamondpick.dd_backend.Dao;

import diamondpick.dd_backend.Entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@Mapper
public interface MessageDao {
    /**
     * 插入消息
     *
     * @param msgId      消息id
     * @param senderId   发送者id
     * @param receiverId 接收者id
     * @param msgType    消息类型
     * @param msgContent 消息内容
     * @param docId      消息所属文档id
     * @param dealId     消息所属处理id
     * @throws DuplicateKeyException           主键冲突异常
     * @throws DataIntegrityViolationException 数据完整性异常
     */
    @Insert("insert into messages (msg_id, sender_id, receiver_id, send_time, msg_type, msg_content, msg_status, msg_doc_id,\n" +
            "                      msg_deal_id)\n" +
            "values (#{param1}, #{param2}, #{param3}, now(), #{param4}, #{param5}, 0, #{param6}, #{param7})")
    public void insertMsg(String msgId, String senderId, String receiverId, int msgType, String msgContent, String docId, int dealId) throws DuplicateKeyException, DataIntegrityViolationException;

    /**
     * 更新消息状态，标记已读
     *
     * @param msgId 消息id
     */
    @Update("update messages\n" +
            "set msg_status = 1\n" +
            "where msg_id = #{param1}")
    public void updateStatusToRead(String msgId) throws DataIntegrityViolationException;


    /**
     * 根据用户选择消息
     *
     * @param userId 用户id
     * @return 返回的Message对象额外需要发送者、团队处理中的团队、用户、文档的名称
     */
    @Select("select msg.*,\n" +
            "       us.nickname    as sender_name,\n" +
            "       doc.name       as msg_doc_name,\n" +
            "       td.team_id     as team_id,\n" +
            "       ttd.name       as team_name,\n" +
            "       td.deal_status as deal_status\n" +
            "from messages msg,\n" +
            "     users us,\n" +
            "     documents doc,\n" +
            "     team_deal td,\n" +
            "     teams ttd\n" +
            "where msg.sender_id = us.user_id\n" +
            "  and msg.msg_doc_id = doc.doc_id\n" +
            "  and msg.msg_deal_id = td.deal_id\n" +
            "  and td.team_id = ttd.team_id\n" +
            "  and msg.receiver_id = #{param1}\n" +
            "  and td.deal_status = 0")
    public List<Message> selectMsg(String userId);

    /**
     * 根据用户和消息类型选择消息
     *
     * @param userId  用户id
     * @param msgType 消息类型
     * @return 返回的Message对象额外需要发送者、团队处理中的团队、用户、文档的名称
     */
    @Select("select msg.*,\n" +
            "       us.nickname    as sender_name,\n" +
            "       doc.name       as msg_doc_name,\n" +
            "       td.team_id     as team_id,\n" +
            "       ttd.name       as team_name,\n" +
            "       td.deal_status as deal_status\n" +
            "from messages msg,\n" +
            "     users us,\n" +
            "     documents doc,\n" +
            "     team_deal td,\n" +
            "     teams ttd\n" +
            "where msg.sender_id = us.user_id\n" +
            "  and msg.msg_doc_id = doc.doc_id\n" +
            "  and msg.msg_deal_id = td.deal_id\n" +
            "  and td.team_id = ttd.team_id\n" +
            "  and msg.receiver_id = #{param1}\n" +
            "  and msg.msg_type = #{param2}\n" +
            "  and td.deal_status = 0")
    public List<Message> selectMsgByType(String userId, int msgType);

    /**
     * 查询消息最大id
     *
     * @return 消息最大id
     */
    @Select("select msg_id\n" +
            "from messages\n" +
            "order by convert(msg_id using gbk) desc\n" +
            "limit 1")
    public String selectMaxId();
}
