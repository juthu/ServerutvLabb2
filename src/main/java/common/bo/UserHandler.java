package common.bo;

import common.model.User;
import common.viewModel.ViewUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.security.NoSuchAlgorithmException;

/**
 * Created by luben on 2015-11-07.
 */
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserHandler {
    //static SessionFactory seshF = HibUtil.getSessionFactory();

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    public static long login(ViewUser u){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        User existing = null;
        try {
            existing = (User) em.createNamedQuery("findUserByUsernamePassword")
                    .setParameter("name", u.getUsername()).setParameter("password", u.getPass()).getSingleResult();
        } catch (NoResultException e) {
            return -1;
        }

        em.getTransaction().commit();
        em.close();
        return existing.getU_id();
    }

    static User getUser(long id,EntityManager lem){
        User out=(User) lem.createNamedQuery("findUserById")
                .setParameter("id", id).getSingleResult();
        return out;
    }

    public static boolean register(ViewUser u) throws NoSuchAlgorithmException, UserAlreadyExistExecption {
        em = emf.createEntityManager();
        em.getTransaction().begin();
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

}

