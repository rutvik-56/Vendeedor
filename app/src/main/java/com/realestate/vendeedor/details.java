package com.realestate.vendeedor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class details extends AppCompatActivity {

    Intent i;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView back;
    ImageView ii;
    TextView name,prize,bedroom,bathroom,sqrt,address,parking,rorsell,ema,disx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        i=getIntent();
        name=(TextView)findViewById(R.id.name);prize=(TextView)findViewById(R.id.prize);
        ii=(ImageView)findViewById(R.id.imk);
        rorsell=(TextView) findViewById(R.id.rorsell);
        back=(TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details.super.onBackPressed();
                finish();
            }
        });
        parking=(TextView)findViewById(R.id.parking);
        ema=(TextView)findViewById(R.id.ema);
        disx=(TextView)findViewById(R.id.dis);
        bedroom=(TextView)findViewById(R.id.bedroom);bathroom=(TextView)findViewById(R.id.bathroom);
        address =(TextView)findViewById(R.id.address);
        sqrt=(TextView)findViewById(R.id.sqrt);
        final String namei=i.getStringExtra("i");
        final String namej=i.getStringExtra("j");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");
        myRef.child("alluid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a=dataSnapshot.getValue(String.class);
                final String all[]=a.split(";");
                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        name.setText(dataSnapshot.getValue(String.class)+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("rentorsell").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        rorsell.setText(dataSnapshot.getValue(String.class)+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                myRef.child(all[Integer.parseInt(namei)]).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ema.setText(dataSnapshot.getValue(String.class)+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("discription").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        disx.setText(dataSnapshot.getValue(String.class)+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("parking").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        parking.setText(dataSnapshot.getValue(String.class)+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("prize").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        prize.setText(dataSnapshot.getValue(String.class)+"  INR");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("bedroom").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        bedroom.setText(dataSnapshot.getValue(String.class)+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("bathroom").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        bathroom.setText(dataSnapshot.getValue(String.class)+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("squarefoor").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sqrt.setText(dataSnapshot.getValue(String.class)+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                final String[] xxx = {""};
                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        xxx[0] =dataSnapshot.getValue(String.class)+"";
                        myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("city").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                xxx[0]=xxx[0]+", "+dataSnapshot.getValue(String.class)+"";

                                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("pincode").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        address.setText(xxx[0]+", "+dataSnapshot.getValue(String.class)+".");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                myRef.child(all[Integer.parseInt(namei)]).child("ads"+namej).child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Glide.with(details.this).load(dataSnapshot.getValue(String.class)+"").into(ii);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });





    }
}
