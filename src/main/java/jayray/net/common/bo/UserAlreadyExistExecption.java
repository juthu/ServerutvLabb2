package jayray.net.common.bo;

/**
 * Created by luben on 2015-11-09.
 */
public class UserAlreadyExistExecption extends DBExeption {
    public UserAlreadyExistExecption(){

    }

    public UserAlreadyExistExecption(String msg){
        super(msg);
    }
}
