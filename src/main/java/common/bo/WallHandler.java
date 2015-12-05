package common.bo;

import common.model.User;
import common.model.WallPost;
import common.viewModel.post;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sirena on 2015-11-18.
 */
@Path("/wall")
@Produces("application/json")
@Consumes("application/json")
public class WallHandler {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    public static boolean post(post p) {
        boolean out = true;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            WallPost post = new WallPost();
            User u = UserHandler.getUser(p.getUid(), em);
            post.setUser(u);
            post.setPost(p.getMessage());
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

    public static List<post> getPosts(long username) {
        em = emf.createEntityManager();
        User u = UserHandler.getUser(username, em);
        //TODO should close em
        Collection<WallPost> wposts=u.getWallPost();
        ArrayList<post> out=new ArrayList<>();
        for (WallPost wp: wposts) {
            out.add(new post(wp.getUser().getU_id(),wp.getPost()));
        }
        em.close();
        return out;
    }

    public static post getPost(long id) {
        em = emf.createEntityManager();
        WallPost wp= (WallPost) em.createNamedQuery("findPostById").setParameter("id", id).getSingleResult();
        post out=new post(wp.getUser().getU_id(),wp.getPost());
        em.close();
        return out;
    }

}
