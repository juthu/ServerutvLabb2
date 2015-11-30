package jayray.net.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by luben on 2015-11-07.
 */
@Entity
@Table(name="messages")
public class Messages {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="from_id")
    private int fromId;
    @Column(name="to_id")
    private int toId;
    @Column(name="message")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
