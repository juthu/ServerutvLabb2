package jayray.net.hello;

import com.google.gson.Gson;
import jayray.net.bo.User;
import jayray.net.bo.UserHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sirena on 2016-01-18.
 */
@Path("getpeople")
public class GetPeople{
        static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
        static EntityManager em;

    @GET
    @Produces("application/json")
    public String getPeopleList(){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        Collection<User> people = ( Collection<User>)em.createNamedQuery("findPeople")
                .setParameter("id",(long)242).getResultList();
        ArrayList<Login_id> p = new ArrayList<>();

        for (User item : people) {
            Login_id li = new Login_id();
            li.username = item.getUsername();
            li.password = item.getPassword();
            p.add(li);
        }
        em.close();
        Gson g = new Gson();
        ResultGson rgClass= new ResultGson();
        rgClass.Json = g.toJson(p);
        String lg = g.toJson(rgClass,ResultGson.class);
        return lg;
    }

    public class Login_id {
        public String username;
        public String password;
    }

    public class ResultGson {
        public String Json;
    }
}
