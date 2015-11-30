package jayray.net.bo;

import java.io.*;

/**
 * Created by luben on 2015-11-18.
 */
public class Copy {
    public static Serializable clone(Serializable s) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(s);

        //De-serialization of object
        ByteArrayInputStream bis = new   ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        return (Serializable) in.readObject();
    }
}
