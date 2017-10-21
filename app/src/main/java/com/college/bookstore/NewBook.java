package com.college.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewBook extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    EditText name,author,category,price,country;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);
        firebaseAuth=FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.name);
        author=(EditText)findViewById(R.id.author);
        category=(EditText)findViewById(R.id.category);
        price=(EditText)findViewById(R.id.price);
        country=(EditText)findViewById(R.id.country);
        save=(Button)findViewById(R.id.save);
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),Signin.class));
            finish();
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("Books");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("")){
                    name.setError("Can't be blank");
                    name.requestFocus();
                }else if(author.getText().toString().equals("")){
                    author.setError("Can't be blank");
                    author.requestFocus();
                }else if(category.getText().toString().equals("")){
                    category.setError("Can't be blank");
                    category.requestFocus();
                }else if(price.getText().toString().equals("")){
                    price.setError("Can't be blank");
                    price.requestFocus();
                }else{
                    Book book=new Book(name.getText().toString(),author.getText().toString(),category.getText().toString(),price.getText().toString(),country.getText().toString());
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    String id=databaseReference.push().getKey();
                    databaseReference.child(id).setValue(book);
                    Toast.makeText(getApplicationContext(),"Data Saved Sucessfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Home.class));
                    finish();
                }

            }
        });

    }

}
