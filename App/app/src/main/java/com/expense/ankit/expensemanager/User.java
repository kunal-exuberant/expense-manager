package com.expense.ankit.expensemanager;

/**
 * Created by SAnkit on 7/12/2015.
 */
public class User
{
    String name;
    int userId;
    double balance;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String get(String column)
    {
        if(column == "Name")
        {
            return this.name;
        }
        else if(column == "Balance")
        {
            return this.balance + "";
        }
        else if(column =="email")
        {
            return this.email;
        }
        else
        {
            return "";
        }
    }
    @Override
    public String toString() {
        return this.name;
    }
}