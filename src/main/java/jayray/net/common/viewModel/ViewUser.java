package jayray.net.common.viewModel;
import javax.xml.bind.annotation.XmlRootElement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by luben on 2015-12-05.
 */
@XmlRootElement
public class ViewUser {
    private String username;
    private String pass;

    public ViewUser() {
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public ViewUser(String username, String pass) {

        this.username = username;
        this.pass = pass;
    }

    public void doCrypt(){
        pass=cryptWithMD5(pass);
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

    @Override
    public String toString() {
        return "ViewUser{" +
                "username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
