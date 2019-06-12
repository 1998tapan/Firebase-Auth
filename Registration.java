package com.example.smart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
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

public class Registration extends AppCompatActivity {

    Button register_btn;
    EditText email1,pwd,confirmpwd,fullname,phone;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        register_btn=findViewById(R.id.register_btn);
        email1=findViewById(R.id.email);
        pwd=findViewById(R.id.pwd);
        confirmpwd=findViewById(R.id.confirmpwd);
        fullname=findViewById(R.id.fullname);
        phone=findViewById(R.id.phone);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

    }
    private void goToMain(){

        String email=email1.getText().toString();
        String password=pwd.getText().toString();

        if(!validateRegistration()){
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Registration.this,"Registration successfull !!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                            return;
                        }

                        else {
                            Toast.makeText(Registration.this,"An error occured. Registration unsuccessfull ! Please try again !", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    private boolean validateRegistration() {
        boolean valid=true;

        String email=email1.getText().toString().trim();
        String password=pwd.getText().toString().trim();
        String confirmpassword=confirmpwd.getText().toString().trim();
        String name=fullname.getText().toString().trim();
        String mob=phone.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            email1.setError("Required");
            valid=false;
            Toast.makeText(Registration.this,"Please fill email",Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)){
            pwd.setError("Required");
            valid=false;
            Toast.makeText(Registration.this,"Please fill password",Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(confirmpassword)){
            confirmpwd.setError("Required");
            valid=false;
            Toast.makeText(Registration.this,"Please fill confirm password",Toast.LENGTH_SHORT).show();
        }

        if(password.length()<8){
            valid=false;
            Toast.makeText(Registration.this,"Password should be more than 8 characters",Toast.LENGTH_SHORT).show();
        }

        if(!(password.equals(confirmpassword))){
            valid=false;
            Toast.makeText(Registration.this,"Password and Confirm Password should match",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(email)){
            email1.setError("Required");
            valid=false;
            Toast.makeText(Registration.this,"Please fill email",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(mob)){
            phone.setError("Required");
            valid=false;
            Toast.makeText(Registration.this,"Please fill all fields",Toast.LENGTH_SHORT).show();

        }

        if(TextUtils.isEmpty(name)){
            fullname.setError("Required");
            valid=false;
            Toast.makeText(Registration.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
        }


        return valid;
    }

}
