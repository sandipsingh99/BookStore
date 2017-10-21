package com.college.bookstore;

/**
 * Created by Deepak on 23-Sep-17.
 */

public class Book {
    public String bookname;
    public String authore;
    public String catogry;
    public  String country;
    public String price;
    public Book(){}

    public Book(String bookname, String authore, String catogry, String price,String country) {
        this.setBookname(bookname);
        this.setAuthore(authore);
        this.setCatogry(catogry);
        this.setPrice(price);
        this.setCountry(country);

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
