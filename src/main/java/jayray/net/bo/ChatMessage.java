package jayray.net.bo;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by sirena on 2015-11-10.
 */
@Embeddable
@Table(name="chatmessage")
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

   // private long messageId;
    private String message;
   // private long to_id;
   // private Profile from;
  //  private Profile to;

    private User user;
    //private long u_id;

   /* @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", unique=true,nullable = false)
    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }*/

    /*@Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @org.hibernate.annotations.Parameter(name = "property", value = "user"))
    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }*/

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    @ManyToOne(optional = false)

   // @ManyToOne(cascade= CascadeType.ALL)
   // @JoinColumn(name="u_id",insertable = false, updatable = false)

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

 /*   @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="from")skkkiioo
    public Profile getFrom() {
        return from;
    }

    public void setFrom(Profile from) {
        this.from = from;
    }
*/
   /* @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name="chat_to_from",
            joinColumns=@JoinColumn(name="u_id"),
            inverseJoinColumns=@JoinColumn(name="from_id")
    )*/

   /* @ManyToOne
    @JoinTable(name="chat_table",
            joinColumns={@JoinColumn(name="b_id", insertable=false,updatable=false)},
            inverseJoinColumns={@JoinColumn(name="a_id", insertable=false,updatable=false)})
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }*/
    /*
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @org.hibernate.annotations.Parameter(name = "property", value = "user"))
    public long getTo_id() {
        return to_id;
    }

    public void setTo_id(long to_id) {
        this.to_id = to_id;
    }
    @ManyToOne(cascade = CascadeType.ALL)

    public Profile getTo() {
        return to;
    }

    public void setTo(Profile to) {
        this.to = to;
    }*/
}
