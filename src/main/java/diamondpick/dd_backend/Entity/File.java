package diamondpick.dd_backend.Entity;

import diamondpick.dd_backend.Exception.NotExist.DocNotExist;

import java.util.Date;

public interface File {
    /**
     * @return 返回1为文档，2为文件夹
     */
    public int getType();
    public String getName();
    public String getFileId();
    public String getCreatorName();
    public String getCreatorId();
    public String getDeleterName();
    public String getDeleterId();
    /**@return 文件夹不必返回有意义的值*/
    public String getModifierName();
    /**@return 文件夹不必返回有意义的值*/
    public String getModifierId();
    /**@return 文件夹不必返回有意义的值*/
    public Date getModifyTime();
    public Date getDeleteTime();
    public Date getCreateTime();
    /**@return 文件夹不必返回有意义的值*/
    public String getSize();
}
