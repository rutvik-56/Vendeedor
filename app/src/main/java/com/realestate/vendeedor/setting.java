package com.realestate.vendeedor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class setting extends Fragment {


    public setting() {
        // Required empty public constructor
    }
    FirebaseDatabase database;
    DatabaseReference myRef;

    TextView t1,t2;
    Button logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v5=inflater.inflate(R.layout.fragment_setting, container, false);
        database = FirebaseDatabase.getInstance();
        logout=(Button) v5.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                Intent i=new Intent(v5.getContext(),login.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getReference("user").child(currentFirebaseUser.getUid());
        t1=v5.findViewById(R.id.name);
        t2=v5.findViewById(R.id.ads);

        myRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                t1.setText(""+dataSnapshot.getValue(String.class));
            }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });

        myRef.child("totalads").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                t2.setText(""+dataSnapshot.getValue(String.class) + "  Posts");
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return v5;
    }

}
