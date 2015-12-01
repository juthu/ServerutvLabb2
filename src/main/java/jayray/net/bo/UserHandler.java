package jayray.net.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by luben on 2015-11-07.
 */

@Path("user")
public class UserHandler {
    //static SessionFactory seshF = HibUtil.getSessionFactory();

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;
    @GET
    @Produces("text/plain")
    public String echo(@QueryParam("m") String message) {
        return "echo: hest jullanebest" + message;
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public static User login(@QueryParam("name") String username,@QueryParam("password") String password){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        User existing = null;
        try {
            existing = (User) em.createNamedQuery("findUserByUsernamePassword")
                    .setParameter("name", username).setParameter("password", cryptWithMD5(password)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        em.getTransaction().commit();
        em.close();
        return existing;
    }

    @GET
    @Path("/getUserId")
    @Produces(MediaType.APPLICATION_JSON)
    static User getUser(@QueryParam("id") long id, EntityManager lem){

       /* List<User> users = new ArrayList<User>();
        listOfCountries=createCountryList();

        for (Country country: listOfCountries) {
            if(country.getId()==id)
                return country;
*/
        User out=(User) lem.createNamedQuery("findUserById")
                .setParameter("id", id).getSingleResult();
        return out;
    }

   @POST
   @Path("/register")
   @Consumes(MediaType.APPLICATION_JSON)
   public static boolean register(User userIn) throws NoSuchAlgorithmException, UserAlreadyExistExecption {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        User existing = null;
        try {
            existing = (User) em.createNamedQuery("findUserByUsername")
                    .setParameter("name", userIn.getUsername()).getSingleResult();

        } catch (NoResultException e1) {

            User user = new User();
            user.setUsername(userIn.getUsername());//TODO check email
            user.setPassword(cryptWithMD5(userIn.getPassword()));
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

    static String cryptWithMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            //Logger.getLogger(CryptWithMD5.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


    }

}

