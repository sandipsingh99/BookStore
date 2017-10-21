package com.college.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Deepak on 24-Sep-17.
 */

public class Booksadapter extends ArrayAdapter<Book> {

    private Activity context;
    private List<Book> bookdata;
    private List<Book> arraylist;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String type;
    public Booksadapter(Activity context, List<Book> bookdata,String type) {
        super(context, R.layout.book_layout,bookdata);
        this.context=context;
        this.bookdata=bookdata;
        this.firebaseAuth=FirebaseAuth.getInstance();
        this.arraylist = new ArrayList<Book>();
        this.arraylist.addAll(bookdata);
        this.type=type;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listviewitems=inflater.inflate(R.layout.book_layout,null,true);
        TextView name=(TextView)listviewitems.findViewById(R.id.name);
        TextView author=(TextView)listviewitems.findViewById(R.id.author);
        TextView catgory=(TextView)listviewitems.findViewById(R.id.catgory);
        TextView price=(TextView)listviewitems.findViewById(R.id.price);
        TextView country=(TextView)listviewitems.findViewById(R.id.country);
        Button buy=(Button)listviewitems.findViewById(R.id.buy);
        buy.setClickable(true);
        if(type.equals("cart")){
            buy.setVisibility(View.GONE);
        }
        final Book books=bookdata.get(position);
        name.setText(books.bookname);
        author.setText("Author:- "+books.authore);
        catgory.setText("Catgory:- "+books.catogry);
        price.setText("price:- "+books.price);
        country.setText("country:- "+books.country);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid()+"cart");
                String id=databaseReference.push().getKey();
                databaseReference.child(id).setValue(books);
            }
        });
        return listviewitems;
    }

    public void booksearch(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bookdata.clear();
        if (charText.length() == 0) {
            bookdata.addAll(arraylist);
        } else {
            for (Book wp : arraylist) {
                if (wp.getBookname().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    bookdata.add(wp);
                } else if (wp.getAuthore().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    bookdata.add(wp);
                } else if (wp.getCatogry().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    bookdata.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
