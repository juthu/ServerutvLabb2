package jayray.net.common.bo;

import com.google.gson.Gson;
import jayray.net.common.model.User;
import jayray.net.common.viewModel.Follower;
import jayray.net.common.viewModel.ProfileList;
import jayray.net.common.viewModel.profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by luben on 2015-11-26.
 */
@Path("/friends/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FriendHandler {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    @POST
    public static boolean addFollower(String json){
        Gson gson=new Gson();
        Follower a=gson.fromJson(json,Follower.class);

        boolean out=true;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            User u = UserHandler.getUser(a.getMe(),em);
           // TypedQuery<User> query = em.createQuery(
            //        "SELECT new User(c.u_id) from User c where c.username = :following", User.class).setParameter("following",a.getFollowing());
            //User tmp = query.getSingleResult();
            User f = UserHandler.getUser(a.getFollowing(),em);
            Collection<User> follow=u.getFollow();
            if(follow.contains(f)){
                out=false;
                em.getTransaction().rollback();
            }else {
                follow.add(f);
                //f.getFollowed().add(u);
                //em.merge(f);
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

    @GET
    @Path("followers/{id}")
    public static String getFollowers(@PathParam("id") long id){//TODO id only
        em = emf.createEntityManager();
        User me = UserHandler.getUser(id,em);
        Collection<User> out= me.getFollow();

        ArrayList<profile> followers= new ArrayList<>();
        for (User u:out) {
            followers.add(new profile(u.getU_id(), u.getProfile().getName(), u.getProfile().getAge(), u.getProfile().getIsFemale(), u.getProfile().getDescription()));
        }
        System.out.println(out);
        em.close();
        ProfileList outList = new ProfileList();
        outList.setList(followers);
        return new Gson().toJson(outList);
    }


    @GET
    @Path("following/{id}")
    public static String countFollowing(@PathParam("id") long id){//TODO id only
        em = emf.createEntityManager();
        User user = UserHandler.getUser(id,em);
         Collection<User> out= user.getFollowed();
        ArrayList<profile> following= new ArrayList<>();
        for (User u:out) {
            following.add(new profile(u.getU_id(), u.getProfile().getName(), u.getProfile().getAge(), u.getProfile().getIsFemale(), u.getProfile().getDescription()));
        }
//        List out = em.createNativeQuery("" +
//                "SELECT user.u_id,user.username " +
//                "FROM tbl_friends " +
//                "INNER JOIN user " +
//                "ON user.u_id = tbl_friends.f_id " +
//                "WHERE f_id =:fid").setParameter("fid",u.getU_id()).getResultList();
        em.close();
        ProfileList outList = new ProfileList();
        outList.setList(following);
        return new Gson().toJson(outList);
    }
//
//    public class custom{
//        public long u_id;
//        public String username;
//    }

}
