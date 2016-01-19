package jayray.net.hello;

import com.google.gson.Gson;
import jayray.net.bo.User;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by sirena on 2016-01-18.
 */

@Path("poke")
public class Poke {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pres_comm");
    static EntityManager em;
    String resp;
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String poke_person(String rg){
        Gson gson = new Gson();
        ResultGson lg = gson.fromJson(rg,ResultGson.class);
        Login_id li = gson.fromJson(lg.Json,Login_id.class);
        em=emf.createEntityManager();
        em.getTransaction().begin();
        Object g = (Object) em.createNativeQuery("SELECT gcmid FROM gcm WHERE facebookid = ?1").setParameter(1,li.password).getSingleResult();
        User u = (User) em.createNamedQuery("findUserByUsername")
                .setParameter("name", li.password).getSingleResult();
        User from = (User) em.createNamedQuery("findUserByUsername")
                .setParameter("name", li.username).getSingleResult();
        //em.persist(p);
        em.getTransaction().commit();
        em.close();
        resp = "TJO";

            try {
            // Prepare JSON containing the GCM message content. What to send and where to send.
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
                jData.put("message", "By: "+from.getPassword());
                    jGcmData.put("to", g.toString());


                // What to send in GCM message.
                jGcmData.put("data", jData);
            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + "AIzaSyCwiTD3vFP_bxBSedJ7eQ9KhhMczU3_NBo");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
             resp = IOUtils.toString(inputStream);
            System.out.println(resp);
            System.out.println("Check your device/emulator for notification or logcat for " +
                    "confirmation of the receipt of the GCM message.");
        } catch (IOException e) {
            System.out.println("Unable to send GCM message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
        } catch (JSONException e) {

            }
        return g.toString();
    }
    public class Login_id {
        public String username;
        public String password;
    }

    public class ResultGson {
        public String Json;
    }
}
