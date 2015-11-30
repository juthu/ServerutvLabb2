package jayray.net.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

/**
 * Created by luben on 2015-11-26.
 */
public class FriendHandler {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    public static boolean addFollower(long followerName,long following){
        boolean out=true;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            User u = UserHandler.getUser(followerName,em);
           //TODO: fixa så att denna quer funkar
            // TypedQuery<User> query = em.createQuery(
           //         "SELECT new User(c.u_id) from User c where c.username = :following", User.class).setParameter("following",following);
           // User tmp = query.getSingleResult();
            User f = UserHandler.getUser(following,em);
            Collection<User> follow=u.getFollow();
            if(follow.contains(f)){
                out=false;
                em.getTransaction().rollback();
            }else {
                follow.add(f);
                f.getFollowed().add(u);
                em.merge(f);
                em.merge(u);
                em.getTransaction().commit();
            }
        }catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
            out=false;
        }finally {
            em.close();
            return out;
        }

    }

    @Path("/getFollowers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static Collection<User> getFollowers(@QueryParam("id") long user){
        em = emf.createEntityManager();
        User u = UserHandler.getUser(user,em);
        Collection<User> out= u.getFollow();
        System.out.println(out);
        em.close();
        return out;
    }

    @Path("/getNrFollowing")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static int countFollowing(@QueryParam("id") long user){
        em = emf.createEntityManager();
        User u = UserHandler.getUser(user,em);
        // Collection<User> out= u.getFollowed();
        List out = em.createNativeQuery("" +
                "SELECT user.u_id,user.username " +
                "FROM tbl_friends " +
                "INNER JOIN user " +
                "ON user.u_id = tbl_friends.f_id " +
                "WHERE f_id =:fid").setParameter("fid",u.getU_id()).getResultList();
        em.close();

        return out.size();
    }
//
//    public class custom{
//        public long u_id;
//        public String username;
//    }

}
