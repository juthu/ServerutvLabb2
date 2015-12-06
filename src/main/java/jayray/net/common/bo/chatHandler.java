package jayray.net.common.bo;

import com.google.gson.Gson;
import jayray.net.common.model.ChatMessage;
import jayray.net.common.viewModel.message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luben on 2015-11-28.
 */
@Path("/chat/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class chatHandler {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    @GET
    @Path("{id}/{other}")
    public static List<message> getMessages(@PathParam("id") long me,@PathParam("other") long other) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        List<ChatMessage> out = em.createNamedQuery("getChat").setParameter("sender", me).setParameter("receiver", other).getResultList();
        em.getTransaction().commit();


        ArrayList<message> outMessage = new ArrayList<>();
        for (ChatMessage msg: out) {
            outMessage.add(new message(msg.getSender(),msg.getReceiver(),msg.getMessage()));
        }
        em.close();
        return outMessage;
    }

    @POST
    public static boolean sendMessage(String json){
        Gson gson = new Gson();
        message m = gson.fromJson(json,message.class);
        em = emf.createEntityManager();
        em.getTransaction().begin();
        ChatMessage msg = new ChatMessage();
        msg.setMessage(m.getMessage());
        msg.setReceiver(m.getRecvier());
        msg.setSender(m.getSender());
        em.persist(msg);
        em.getTransaction().commit();
        em.close();
        return true;
    }

}
