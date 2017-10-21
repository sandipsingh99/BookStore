package com.college.bookstore;

/**
 * Created by Deepak on 23-Sep-17.
 */

public class MyOrderdetails {
    public String type;
    public String name;
    public String email;
    public String number;
    public String address;
    public String bookname;


    public MyOrderdetails(){}

    public MyOrderdetails(String type, String name, String email, String number, String address, String bookname) {
        this.type=type;
        this.name=name;
        this.email=email;
        this.number=number;
        this.address=address;
        this.bookname=bookname;

    }


}
