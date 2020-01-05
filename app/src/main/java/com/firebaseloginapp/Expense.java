package com.firebaseloginapp;

/**
 * Created by Eesha on 24-10-2018.
 */

public class Expense {


    private String amount;
    private String type;
    //private String date;
    public Expense(){

    }

    public Expense( String amount,String type) {
        //this.expenseid = expenseid;
        this.type=type;
        this.amount = amount;
        //this.date=date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    //public String getDate() {
      //  return date;
    //}

    //public void setDate(String date) {
      //  this.date = date;
    //}

    //public String getExpenseid() {
        //return expenseid;
    //}

    //public void setExpenseid(String expenseid) {
        //this.expenseid = expenseid;
    //}


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
