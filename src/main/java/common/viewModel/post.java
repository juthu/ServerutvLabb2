package common.viewModel;

/**
 * Created by luben on 2015-12-05.
 */
public class post {
    private long uid;
//    private long id;
    private String message;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public post(long uid, long id, String message) {
//
//        this.uid = uid;
//        this.id = id;
//        this.message = message;
//    }

    public post(long uid, String message) {
        this.uid = uid;
        this.message = message;
    }
}
