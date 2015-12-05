package common.viewModel;

/**
 * Created by luben on 2015-12-05.
 */
public class message {
    private long sender;
    private long recvier;
    private String message;

    public message(long sender, long recvier) {
        this.sender = sender;
        this.recvier = recvier;
    }

    public long getSender() {

        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getRecvier() {
        return recvier;
    }

    public void setRecvier(long recvier) {
        this.recvier = recvier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public message(long sender, long recvier, String message) {

        this.sender = sender;
        this.recvier = recvier;
        this.message = message;
    }
}
