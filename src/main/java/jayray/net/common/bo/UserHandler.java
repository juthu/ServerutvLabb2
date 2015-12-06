package jayray.net.common.bo;

import com.google.gson.Gson;
import jayray.net.common.model.User;
import jayray.net.common.viewModel.profile;
import jayray.net.common.viewModel.ViewUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;

/**
 * Created by luben on 2015-11-07.
 */
@Path("/users/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserHandler {
    //static SessionFactory seshF = HibUtil.getSessionFactory();

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    //rest/users/login
    @POST
    @Path("login")
    public static profile login(String json){
        Gson gson = new Gson();
        ViewUser u=gson.fromJson(json,ViewUser.class);
        System.out.println("login"+u);
        em = emf.createEntityManager();
        em.getTransaction().begin();
        User existing = null;
        try {
            existing = (User) em.createNamedQuery("findUserByUsernamePassword")
                    .setParameter("name", u.getUsername()).setParameter("password", u.getPass()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        em.getTransaction().commit();
        em.close();
        System.out.println(existing.getU_id());
        profile out =new profile();
        out.setUid(existing.getU_id());
        return out;
    }

    static User getUser(long id,EntityManager lem){
        User out=(User) lem.createNamedQuery("findUserById")
                .setParameter("id", id).getSingleResult();
        return out;
    }

    //rest/users/reg
    @POST
    @Path("reg")
    public static boolean register(String json) throws NoSuchAlgorithmException, UserAlreadyExistExecption {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        Gson gson = new Gson();
        ViewUser u=gson.fromJson(json,ViewUser.class);
        User existing = null;
        try {
            existing = (User) em.createNamedQuery("findUserByUsername")
                    .setParameter("name", u.getUsername()).getSingleResult();

        } catch (NoResultException e1) {

            User user = new User();
            user.setUsername(u.getUsername());//TODO check email
            user.setPassword(u.getPass());
            ProfileHandler.setDefaultProfile(user, em);
            em.persist(user);
            // em.detach(u);
            // em.refresh(u);
            em.getTransaction().commit();
            em.close();
        }
        if (existing != null) {
            throw new UserAlreadyExistExecption("user already exists");
        }

        return true;
    }

    @GET
    @Path("hejsan/{id}/{msg}")
    public String hello(@PathParam("id") int id,@PathParam("msg") String msg){
        return "hej, "+id+msg;
    }

    @POST
    @Path("hej")
    public String hello(String json){
        System.out.println(json);
        Gson gson = new Gson();
        System.out.println( gson.fromJson(json,ViewUser.class));
        return "hej";
    }

}

