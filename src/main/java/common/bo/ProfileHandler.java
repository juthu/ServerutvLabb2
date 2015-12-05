package common.bo;

import common.model.Profile;
import common.model.User;
import common.viewModel.profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sirena on 2015-11-18.
 */
@Path("/profile")
@Produces("application/json")
@Consumes("application/json")
public class ProfileHandler{

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;
   // static SessionFactory seshF = HibUtil.getSessionFactory();

    public static profile getProfile(long username)  {//TODO id only
        em = emf.createEntityManager();
        em.getTransaction().begin();

        User existing = null;
        try {
            existing = (User) em.createNamedQuery("findUserById")
                    .setParameter("id", username).getSingleResult();
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

    public static Collection<profile> search(profile search,profile me){
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
