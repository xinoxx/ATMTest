package Server.Model;

import java.io.Serializable;

//要序列化！！！！！！！！！！
public class ClientInfo implements Serializable {

    private String id ;
    private String password ;
    private double balance ;

    public ClientInfo(){}

    public ClientInfo(String id , String password , double balance ){
        this.id = id ;
        this.password = password ;
        this.balance = balance ;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
