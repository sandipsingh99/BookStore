package com.college.bookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    List<Book> bookDetailses;
    ListView booklistview;

    private ProgressDialog dialog;
    Booksadapter bookList;
    TextView checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
        checkout=(TextView)findViewById(R.id.checkout);
        booklistview=(ListView)findViewById(R.id.booklistview);
        firebaseAuth= FirebaseAuth.getInstance();
        bookDetailses=new ArrayList<>();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid()+"cart");

        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),Signin.class));
            finish();
        }
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BuyBook.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookDetailses.clear();
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren() ){
                    Book bookList=bookSnapshot.getValue(Book.class);
                    bookDetailses.add(bookList);
                }
                if (bookDetailses.size()==0){
                }
                dialog.hide();
                bookList=new Booksadapter(Cart.this,bookDetailses,"cart");
                booklistview.setAdapter(bookList);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
