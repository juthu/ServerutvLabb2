package jayray.net.common.bo;

import com.google.gson.Gson;
import jayray.net.common.model.User;
import jayray.net.common.model.WallPost;
import jayray.net.common.viewModel.post;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sirena on 2015-11-18.
 */
@Path("/wall/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WallHandler {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    @POST
    public static boolean post(String json) {
        Gson gson = new Gson();
        post p =gson.fromJson(json,post.class);
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

    @GET
    @Path("{id}")
    public static List<post> getPosts(@PathParam("id") long id) {//magi med id
        em = emf.createEntityManager();
        User u = UserHandler.getUser(id, em);
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
