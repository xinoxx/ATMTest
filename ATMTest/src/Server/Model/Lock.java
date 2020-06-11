package Server.Model;

import java.io.Serializable;

public class Lock implements Serializable {

    private String id;
    private String password ;
    private int error ;
    private int lock ;

    public Lock(){}

    public Lock(String id , String password , int error , int lock ){
        this.id = id ;
        this.password = password ;
        this.error = error ;
        this.lock = lock ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }
}
