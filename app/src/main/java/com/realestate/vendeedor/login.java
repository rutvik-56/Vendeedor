package com.realestate.vendeedor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    Button signup;
    ImageView signin;
    TextView tt;
    EditText email,password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup=(Button)findViewById(R.id.signup);
        signin=(ImageView)findViewById(R.id.signin);
        mAuth = FirebaseAuth.getInstance();
        tt=(TextView)findViewById(R.id.forgot);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        sharedpreferences = getApplicationContext().getSharedPreferences("hello", 0);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        if(!(sharedpreferences.getBoolean("updatef",true)))
        {
            setContentView(R.layout.activity_main);
            Intent i = new Intent(login.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            setContentView(R.layout.activity_login);
        }
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1=email.getText().toString()+"";
                if( email1.equals(""))
                {
                        email.setError("Required");
                }
                else
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(login.this, "Email Sent successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(login.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(login.this,SignUp.class);
                startActivity(i);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1=email.getText().toString()+"";
                String pass1=password.getText().toString()+"";
                if( email1.equals("") || pass1.equals(""))
                {
                    if (email1.equals(""))
                    {
                        email.setError("Required");

                    }
                    if (pass1.equals(""))
                    {
                        password.setError("Required");

                    }
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(email1, pass1)
                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent i=new Intent(login.this,MainActivity.class);
                                        Toast.makeText(login.this, "Successfull", Toast.LENGTH_SHORT).show();
                                        editor.putBoolean("updatef",false);
                                        editor.commit();
                                        startActivity(i);
                                        finish();

                                    } else {

                                        Toast.makeText(login.this, "Password or Email is incorrect", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

    }
}

