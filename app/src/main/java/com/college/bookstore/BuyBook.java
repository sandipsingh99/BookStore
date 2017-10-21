package com.college.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyBook extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference3;
    private DatabaseReference databaseReference1;
    private FirebaseAuth firebaseAuth;
    private List<Book> bookDetailses;
    String type="Buy";
    EditText et_name,et_email,et_mobile,et_addres;
    String bookname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);
        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_addres=(EditText)findViewById(R.id.et_address);
        firebaseAuth= FirebaseAuth.getInstance();
        bookDetailses=new ArrayList<>();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference1= FirebaseDatabase.getInstance().getReference("Order");
        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid()+"cart");

        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),Signin.class));
            finish();
        }

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
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void submit(View view){
        if(et_name.getText().toString().equals("")){
            et_name.setError("Can't be Blank");
            et_name.requestFocus();
        }else if(et_email.getText().toString().equals("")){
            et_email.setError("Can't be Blank");
            et_email.requestFocus();
        }else if(!(et_mobile.getText().toString().length()==10)){
            et_mobile.setError("Contact number must have 10 digits");
            et_mobile.requestFocus();
        }else if(et_addres.getText().toString().equals("")){
            et_addres.setError("Can't be Blank");
            et_addres.requestFocus();
        }else{
            for (int i=0;i<bookDetailses.size();i++){
                final Book bookDetailsss=bookDetailses.get(i);
                bookname=bookname+bookDetailsss.getBookname()+","+bookDetailsss.getAuthore()+","+bookDetailsss.getPrice()+"\n";
            }
            MyOrderdetails orderdetailsDetails=new MyOrderdetails(type,et_name.getText().toString(),et_email.getText().toString(),et_mobile.getText().toString(),et_addres.getText().toString(),bookname);
            FirebaseUser user=firebaseAuth.getCurrentUser();
            String id=databaseReference1.push().getKey();
            databaseReference1.child(id).setValue(orderdetailsDetails);
            databaseReference3= FirebaseDatabase.getInstance().getReference(user.getUid()+"cart");
            databaseReference3.removeValue();
            Toast.makeText(getApplicationContext(),"Order placed successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
    }
}
