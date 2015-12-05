package common.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sirena on 2015-11-12.
 */

@NamedQueries({
        @NamedQuery(
                name = "findPostById",
                query = "from WallPost p where p.id = :id"
        )
})
@Entity
@Table(name = "wall_post")
public class WallPost implements Serializable{
    private static final long serialVersionUID = 1L;

    private long postId;
    private String post;
    private User user;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", unique=true,nullable = false)
    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="u_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
