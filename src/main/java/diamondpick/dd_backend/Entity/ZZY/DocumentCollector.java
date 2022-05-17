package diamondpick.dd_backend.Entity.ZZY;

import diamondpick.dd_backend.Entity.yyh.User;

import javax.persistence.*;

@Entity
public class DocumentCollector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "d_id")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "u_id")
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
