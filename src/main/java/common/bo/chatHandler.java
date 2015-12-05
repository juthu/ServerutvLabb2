package common.bo;

import common.model.ChatMessage;
import common.viewModel.message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luben on 2015-11-28.
 */
public class chatHandler {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;

    public static List<message> getMessages(message messages) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        List<ChatMessage> out = em.createNamedQuery("getChat").setParameter("sender", messages.getSender()).setParameter("receiver", messages.getRecvier()).getResultList();
        em.getTransaction().commit();


        ArrayList<message> outMessage = new ArrayList<>();
        for (ChatMessage msg: out) {
            outMessage.add(new message(msg.getSender(),msg.getReceiver(),msg.getMessage()));
        }
        em.close();
        return outMessage;
    }

    public static boolean sendMessage(message m){
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
