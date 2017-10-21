package com.college.bookstore;

/**
 * Created by Deepak on 23-Sep-17.
 */

public class Buydata {
    public String name;
    public String email;
    public String number;
    public String address;
    public String bookname;
    public Buydata(){}

    public Buydata(String bookname, String name, String email, String number, String address) {
        this.bookname=bookname;
        this.name=name;
        this.email=email;
        this.number=number;
        this.address=address;

    }


}
