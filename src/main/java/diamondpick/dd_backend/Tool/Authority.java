package diamondpick.dd_backend.Tool;

public enum Authority{
    canNotSee, //1
    See,     //2
    Comment, //3
    Edit, // 4
    All, //文档所在空间的所有者（或团队）拥有的权限
}
