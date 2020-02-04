package com.realestate.vendeedor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements LocationListener{

    Button signin;
    private FirebaseAuth mAuth;
    String longitude="rr",latitude="rr";
    static String TAG="abcd";
    ImageView signup;
    EditText name,email,password;


    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }

    FirebaseDatabase database;
    SharedPreferences sharedpreferences;
    DatabaseReference myRef;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedpreferences = getApplicationContext().getSharedPreferences("hello", 0);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        if(!(sharedpreferences.getBoolean("updatef",true)))
        {
            setContentView(R.layout.activity_main);
            Intent i = new Intent(SignUp.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            setContentView(R.layout.activity_sign_up);
        }
        signin=(Button)findViewById(R.id.signin);
        checkpermission();
        mAuth = FirebaseAuth.getInstance();
        signup=(ImageView)findViewById(R.id.signup);
        name=(EditText)findViewById(R.id.name);
         database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUp.this,login.class);
                startActivity(i);
finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name1 = name.getText().toString() + "";
                final String email1 = email.getText().toString() + "";
                String pass1 = password.getText().toString() + "";

                if (name1.equals("") || email1.equals("") || pass1.equals("")) {
                    if (name1.equals("")) {
                        name.setError("Required");
                    }

                    if (email1.equals("")) {
                        email.setError("Required");

                    }

                    if (pass1.equals("")) {
                        password.setError("Required");

                    }
                } else if (!isValidEmail(email1)) {
                    email.setError("Not Valid Email");
                } else if (!isValidPassword(pass1)) {
                    password.setError("Passwords must contain at least 8 characters, including letters, numbers and special character");
                } else if (latitude.equals("rr")) {
                    Toast.makeText(SignUp.this, "Sorry we are not fetching your location Please Wait..",Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.createUserWithEmailAndPassword(email1, pass1)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        myRef.child(currentFirebaseUser.getUid()).child("name").setValue(name1);
                                        myRef.child(currentFirebaseUser.getUid()).child("longitude").setValue(longitude);
                                        myRef.child(currentFirebaseUser.getUid()).child("latitude").setValue(latitude);
                                        myRef.child(currentFirebaseUser.getUid()).child("email").setValue(email1);
                                        myRef.child(currentFirebaseUser.getUid()).child("totalads").setValue("0");
                                        myRef.child("alluid").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String emailx = dataSnapshot.getValue(String.class);
                                                if(emailx.equals(""))
                                                {
                                                    emailx=currentFirebaseUser.getUid();
                                                }
                                                else
                                                {
                                                    emailx=currentFirebaseUser.getUid()+ ";"+ emailx;
                                                }

                                                myRef.child("alluid").setValue(emailx);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                            }
                                        });
                                        editor.putBoolean("updatef",false);
                                        editor.commit();
                                                Intent i=new Intent(SignUp.this,MainActivity.class);
                                        Toast.makeText(SignUp.this, "Successfull", Toast.LENGTH_SHORT).show();
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUp.this, "SignUp failed.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }

        });
    }

    public void getLocation()
    {
        try {
            locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,5,this);
        }
        catch (SecurityException e)
        {
            Toast.makeText(SignUp.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    public void checkpermission()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},101);
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude=String.valueOf(location.getLongitude());
        latitude=String.valueOf(location.getLatitude());

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        getLocation();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(SignUp.this, "GPS Enabled",Toast.LENGTH_SHORT).show();
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(SignUp.this, "Please Enable GPS and Internet",Toast.LENGTH_SHORT).show();
    }
}
