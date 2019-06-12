package com.example.smart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MicrophoneInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button register_btn,login_btn;
    EditText email1,pwd;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register_btn=findViewById(R.id.register_btn);
        login_btn=findViewById(R.id.login_btn);
        email1=findViewById(R.id.email);
        pwd=findViewById(R.id.pwd);
        //FirebaseApp.initializeApp(this);
        mAuth= FirebaseAuth.getInstance();

        isLoggedIn();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });
    }

    private void isLoggedIn() {
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            return;
        }
    }


    private void goLogin(){
        String email=email1.getText().toString().trim();
        String password=pwd.getText().toString().trim();

        if(!validateLogin()){
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this,"Login successfull !!",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this,"An error occured. Login unsuccessfull ! Please try again !", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });

    }

    private void goRegister(){
          startActivity(new Intent(getApplicationContext(),Registration.class));
          finish();
    }

    private boolean validateLogin() {
        boolean valid=true;

        String email=email1.getText().toString().trim();
        String password=pwd.getText().toString().trim();

        if(password.length()<8){
            valid=false;
            Toast.makeText(Login.this,"Password should be more than 8 characters",Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)){
            pwd.setError("Required");
            valid=false;
            Toast.makeText(Login.this,"Please fill password",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(email)){
            email1.setError("Required");
            valid=false;
            Toast.makeText(Login.this,"Please fill email",Toast.LENGTH_SHORT).show();
        }

        return valid;
    }
}
