package Server.Model;

import java.io.Serializable;

public class Log implements Serializable {

    private String id ;
    private String date ;
    private double balance ;
    private double lastBalance ;

    public Log(){}

    public Log(String id , String date , double balance , double lastBalance ){
        this.id = id ;
        this.date = date ;
        this.balance = balance ;
        this.lastBalance = lastBalance ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(double lastBalance) {
        this.lastBalance = lastBalance;
    }
}
