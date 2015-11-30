package jayray.net.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sirena on 2015-11-18.
 */

@Path("/profile")
public class ProfileHandler{

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;
   // static SessionFactory seshF = HibUtil.getSessionFactory();

    @GET
    @Path("/getProfile")
    @Produces(MediaType.APPLICATION_JSON)
    public static Profile getProfile(@QueryParam("id") long id) throws IOException, ClassNotFoundException {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        User existing = null;
        try {
            existing = (User) em.createNamedQuery("findUserById")
                    .setParameter("id", id).getSingleResult();
        }catch (NullPointerException e){
            System.out.printf("The user do not exist");
        }catch (NoResultException e){
            System.out.printf("The user do not exist");
        }
        if(existing!=null){
            em.persist(existing);
            em.getTransaction().commit();
            Profile p = existing.getProfile();
            em.close();
            return (Profile) Copy.clone(p);
        }
        return new Profile();

    }


    @POST
    @Path("/updateProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    public static boolean update(Profile profile){
        em=emf.createEntityManager();
        em.getTransaction().begin();
        User u=UserHandler.getUser(profile.getU_id(),em);
        Profile p=u.getProfile();
        p.setAge(profile.getAge());
        p.setDescription(profile.getDescription());
        p.setIsFemale(profile.getIsFemale());
        p.setName(profile.getName());
        //p.setUser(u);
        //em.persist(p);
        em.merge(p);
        em.getTransaction().commit();
        em.close();

        return true;
    }

    @Path("/searchProfile")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static Collection<Profile> search(@QueryParam("search_txt") String search,String exclude) throws IOException, ClassNotFoundException {
        Collection out;//= new ArrayList<SimpleUser>();
        try {
            em = emf.createEntityManager();
            out = em.createNamedQuery("findUserByUsernameContains").setParameter("search", "%"+search+"%").setParameter("exclude", exclude).getResultList();
        } catch (NoResultException e) {
            out = new ArrayList<String>();
            out.add("no user found");
        }finally {
            em.close();
        }
        return out;
    }

        static void setDefaultProfile(User u, EntityManager em){
        Profile p = new Profile();
        p.setAge(-1);
        p.setDescription("update description");
        p.setIsFemale(false);
        p.setName(u.getUsername());
        p.setUser(u);
        u.setProfile(p);
     //   em.refresh(p);
     //   em.merge(p);
        em.persist(p);
//        em.persist(u);
//       // em.detach(u);
//       // em.refresh(u);
//        em.getTransaction().commit();
//        em.close();
    }

}
