package com.college.bookstore;

/**
 * Created by Deepak on 23-Sep-17.
 */

public class Cartdetails {
    public String bookname;
    public String authore;
    public String catogry;
    public String price,booklink;
    public Cartdetails(){}

    public Cartdetails(String bookname, String authore, String catogry, String price, String booklink) {
        this.setBookname(bookname);
        this.setAuthore(authore);
        this.setCatogry(catogry);
        this.setPrice(price);
        this.setBooklink(booklink);
    }

    public String getBooklink() {
        return booklink;
    }

    public void setBooklink(String booklink) {
        this.booklink = booklink;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthore() {
        return authore;
    }

    public void setAuthore(String authore) {
        this.authore = authore;
    }

    public String getCatogry() {
        return catogry;
    }

    public void setCatogry(String catogry) {
        this.catogry = catogry;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
