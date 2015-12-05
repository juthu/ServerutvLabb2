package jayray.net.common.bo;

import jayray.net.common.model.Profile;
import jayray.net.common.model.User;
import jayray.net.common.viewModel.profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sirena on 2015-11-18.
 */
@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileHandler{

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;
   // static SessionFactory seshF = HibUtil.getSessionFactory();

    @GET
    @Path("{id}")
    public static profile getProfile(@PathParam("id") long id)  {//TODO id params magi
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
            Profile p=existing.getProfile();
            return new profile(p.getU_id(),p.getName(),p.getAge(),p.getIsFemale(),p.getDescription());
        }
        return null;

    }

    @POST
    public static boolean update(profile me){
        em=emf.createEntityManager();
        em.getTransaction().begin();
        User u=UserHandler.getUser(me.getUid(),em);
        Profile p=u.getProfile();
        p.setAge(me.getAge());
        p.setDescription(me.getDesc());
        p.setIsFemale(me.isFemale());
        p.setName(me.getName());
        //p.setUser(u);
        //em.persist(p);
        em.merge(p);
        em.getTransaction().commit();
        em.close();

        return true;
    }

    @GET
    @Path("search")
    public static Collection<profile> search(profile search,profile me){//TODO magi med id
        Collection<profile> out = new ArrayList<profile>();
        try {
            em = emf.createEntityManager();
            Collection <Profile> tmp = em.createNamedQuery("findUserByUsernameContains").setParameter("search", "%"+search.getName()+"%").setParameter("exclude", me.getName()).getResultList();

            for (Profile p: tmp) {
               out.add(new profile(p.getU_id(),p.getName(),p.getAge(),p.getIsFemale(),p.getDescription()));
            }
        } catch (NoResultException e) {
            out = new ArrayList<>();
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
