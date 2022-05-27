package diamondpick.dd_backend.zzy.Entity;

import diamondpick.dd_backend.yyh.Entity.User;

public class DocumentCollector {
    private int id;

    private Document document;
    private User user;


    public void setId(int id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
