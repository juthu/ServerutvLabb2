package jayray.net.hello;

import com.google.gson.Gson;
import jayray.net.bo.Profile;
import jayray.net.bo.User;
import jayray.net.bo.UserAlreadyExistExecption;
import jayray.net.bo.UserHandler;
import jayray.net.json.JsonGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sirena on 2016-01-18.
 */

@Path("login")
public class LogIn {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;
    @POST
    @Path("/facebookId")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(String rg) {
        String r = "tjod";
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

        Gson g = new Gson();
        ResultGson lg = g.fromJson(rg,ResultGson.class);
        Login_id li = g.fromJson(lg.Json,Login_id.class);
        User existing = null;
            try {
                existing = (User) em.createNamedQuery("findUserByUsername")
                        .setParameter("name", li.username).getSingleResult();
            } catch (NoResultException e1) {
                User userIn = null;
                User user = new User();
                user.setUsername(li.username);//TODO check email
                user.setPassword(li.password);
                em.persist(user);
                em.getTransaction().commit();
                em.close();
            }
            if (existing != null) {
                r="user already exists";
            }else{
                r="true";
            }
        }catch (Exception e){
            r = e.getMessage();
        }
        return "echo: " +r;
    }

    @POST
    @Path("/gcmId")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String add_gcm(String rg) {
        Gson g = new Gson();
        ResultGson lg = g.fromJson(rg,ResultGson.class);
        Login_id li = g.fromJson(lg.Json,Login_id.class);
        String r = "tjod";
        try {
            em=emf.createEntityManager();
            em.getTransaction().begin();
   em.createNativeQuery("INSERT INTO gcm (gcmid, facebookid) VALUES (?,?)").setParameter(1,li.password).setParameter(2,li.username).executeUpdate();

            //em.persist(p);
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            r = e.getMessage();
        }
        return "echo: " +r;
    }


    public class Login_id {
        public String username;
        public String password;
    }

    public class ResultGson {
        public String Json;
    }
}