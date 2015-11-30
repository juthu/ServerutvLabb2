package jayray.net.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by sirena on 2015-11-18.
 */

public class WallHandler {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public static boolean post(WallPost wp) {
        boolean out = true;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            WallPost post = new WallPost();
            User u = UserHandler.getUser(wp.getUser().getU_id(), em);
            post.setUser(u);
            post.setPost(wp.getPost());
            //users connection
            Collection<WallPost> posts = u.getWallPost();
            posts.add(post);
            u.setWallPost(posts);
            em.persist(post);
            em.merge(u);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            out = false;
        } finally {
            em.close();
            return out;
        }
    }

    @Path("/getPosts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static Collection<WallPost> getPosts(@QueryParam("id") long username) {
        em = emf.createEntityManager();
        User u = UserHandler.getUser(username, em);
        //TODO should close em
        return u.getWallPost();
    }

    @Path("/getPost")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static WallPost getPost(@QueryParam("id") long id) {
        em = emf.createEntityManager();
        return (WallPost) em.createNamedQuery("findPostById").setParameter("id", id).getSingleResult();
    }

}
