package common.bo;

/**
 * Created by luben on 2015-11-09.
 */
public class DBExeption extends Exception {
    public DBExeption(){

    }

    public DBExeption(String errorMsg){
        super(errorMsg);
    }
}
