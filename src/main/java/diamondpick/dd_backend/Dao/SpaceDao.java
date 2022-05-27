package diamondpick.dd_backend.Dao;

public interface SpaceDao {
    /**分配一个新空间*/
    public void insertSpace();

    /**找到最近创建的空间的spaceId*/
    public int selectSpace();

    public void deleteSpace(int spaceId);

}
