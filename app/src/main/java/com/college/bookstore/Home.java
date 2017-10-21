package com.college.bookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

    ListView mylist;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog dialog;
    EditText et_search;
    Booksadapter booksadapter;
    List<Book>  book;
    Button bt_search,bt_cart;
    List<Cartdetails>  cart;
    private DatabaseReference databasecart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mylist=(ListView)findViewById(R.id.mylist);
        bt_cart=(Button)findViewById(R.id.cart);
        bt_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Cart.class));
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        cart=new ArrayList<>();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Books");
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),Signin.class));
            finish();
        }
        firebaseAuth=FirebaseAuth.getInstance();
        databasecart= FirebaseDatabase.getInstance().getReference(user.getUid()+"cart");
        book=new ArrayList<>();
        et_search=(EditText)findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if(et_search.getText().toString().length()==0) {
                    String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                    booksadapter.booksearch(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        bt_search=(Button)findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                booksadapter.booksearch(text);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                book.clear();
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren() ){
                    Book booksdata=bookSnapshot.getValue(Book.class);
                    book.add(booksdata);
                }
               booksadapter=new Booksadapter(Home.this,book,"home");
               mylist.setAdapter(booksadapter);
                mycart();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void mycart(){
        databasecart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cart.clear();
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren() ){
                    Cartdetails bookList=bookSnapshot.getValue(Cartdetails.class);
                    cart.add(bookList);
                }
                if (cart.size()==0){
                    bt_cart.setVisibility(View.GONE);
                }else {
                    bt_cart.setVisibility(View.VISIBLE);
                    bt_cart.setText(cart.size()+" Cart Items");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.book:
                startActivity(new Intent(getApplicationContext(),NewBook.class));
                return true;
            case R.id.signout:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Signin.class));
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
