package com.college.bookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    private ProgressDialog progressDialog;
    EditText et_email, et_password;
    Button bt_signup;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        }
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_signup = (Button) findViewById(R.id.bt_signup);
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void signin(View view) {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!email.matches(emailPattern)) {
            et_email.requestFocus();
        }else if (password.equals("")) {
            et_password.setError("Password Can't Blank");
            et_password.requestFocus();
        }else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("please wait.");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login sucess", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Sign in fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
