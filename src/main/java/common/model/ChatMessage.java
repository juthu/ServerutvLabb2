package common.model;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sirena on 2015-11-10.
 */

@NamedQueries({
        @NamedQuery(
                name = "getChat",
                query = "from ChatMessage cm where (cm.sender = :sender and cm.receiver = :receiver) or (cm.sender = :receiver and cm.receiver = :sender)"
        )
})
@Entity
@Table(name="chatmessage")
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private long sender;
    private long receiver;
    private long id;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", unique=true,nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name="receiver", nullable = false)
    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    @Column(name="message", nullable = false)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Column(name="sender", nullable = false)
    public long getSender() {
        return sender;
    }

    public void setSender(long user) {
        this.sender = user;
    }

    public String toString(){
        return  message;
    }


}
