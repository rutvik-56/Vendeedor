package com.realestate.vendeedor;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Object> objects = new ArrayList<>();
    FirebaseDatabase database;
    RecyclerView recyclerView;
    BubbleTabBar bn;
    DatabaseReference myRef,myref1;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user").child("alluid");
            myref1 = database.getReference("user");
        new run().execute();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        bn=(BubbleTabBar)findViewById(R.id.bubbleTabBar);
        bn.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {

                setting ss=new setting();
                upho up=new upho();
                search s=new search();
                LinearLayout linearr = (LinearLayout) findViewById(R.id.two);
                LinearLayout linear2r = (LinearLayout) findViewById(R.id.one);
                switch (i)
                {
                    case R.id.homef:

                        LinearLayout.LayoutParams params2zr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        LinearLayout.LayoutParams paramsxzr = new LinearLayout.LayoutParams(0, 0);
                        params2zr.weight = 1.0f;
                        paramsxzr.weight=0f;
                        linear2r.setLayoutParams(paramsxzr);
                        linearr.setLayoutParams(params2zr);
                        break;

                    case R.id.log:
                        LinearLayout.LayoutParams params22zr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        LinearLayout.LayoutParams params2xzr = new LinearLayout.LayoutParams(0, 0);
                        params22zr.weight = 1.0f;
                        params2xzr.weight=0f;
                        linear2r.setLayoutParams(params2xzr);
                        linearr.setLayoutParams(params22zr);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,s).commit();
                        break;

              case R.id.upload:
                  LinearLayout.LayoutParams params2r = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                  LinearLayout.LayoutParams paramsxr = new LinearLayout.LayoutParams(0, 0);
                  paramsxr.weight = 0f;
                  params2r.weight=1.0f;
                  linear2r.setLayoutParams(params2r);
                  linearr.setLayoutParams(paramsxr);
                  getSupportFragmentManager().beginTransaction().replace(R.id.container,up).commit();
                break;

            case R.id.setting:
                LinearLayout.LayoutParams params2fr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams paramsxfr = new LinearLayout.LayoutParams(0, 0);
                paramsxfr.weight = 0f;
                params2fr.weight=1.0f;
                linear2r.setLayoutParams(params2fr);
                linearr.setLayoutParams(paramsxfr);
               getSupportFragmentManager().beginTransaction().replace(R.id.container,ss).commit();
                break;
                }
            }
        });

    }



    private ArrayList<Object> getObject() {
       // objects.add(getHorizontalData().get(0));
        objects.add(getVerticalData().get(0));
        return objects;
    }

    static ArrayList<String> nameall= new ArrayList<String>();
    static ArrayList<String> fori= new ArrayList<String>();
    static ArrayList<String> forj= new ArrayList<String>();
    static ArrayList<String> prizeall= new ArrayList<String>();
    static ArrayList<String> squarefootall= new ArrayList<String>();
    static ArrayList<String> rentall= new ArrayList<String>();
    static ArrayList<String> imageall= new ArrayList<String>();

    public static ArrayList<SingleVertical> getVerticalData() {
        ArrayList<SingleVertical> singleVerticals = new ArrayList<>();
        for (int i=0;i<nameall.size();i++)
        {
            singleVerticals.add(new SingleVertical(nameall.get(i),prizeall.get(i)+"  INR", squarefootall.get(i)+" sq. ft", "15 days ago", rentall.get(i),imageall.get(i)));
        }

        return singleVerticals;
    }


    public static ArrayList<SingleHorizontal> getHorizontalData() {
        ArrayList<SingleHorizontal> singleHorizontals = new ArrayList<>();
        singleHorizontals.add(new SingleHorizontal(R.drawable.download, "Charlie Chaplin", "Sir Charles Spencer ", "2010/2/1"));
        singleHorizontals.add(new SingleHorizontal(R.drawable.download, "Mr.Bean", "Mr. Bean is a", "2010/2/1"));
        singleHorizontals.add(new SingleHorizontal(R.drawable.download, "Jim Carrey", "James Eugeneer...", "2010/2/1"));
        return singleHorizontals;
    }




    class run extends AsyncTask
    {
        String check="";
        @Override
        protected void onPostExecute(Object o) {
           new CountDownTimer(60000, 600) {
                public void onTick(long millisUntilFinished) {
                            if(check.equals("rrr"))
                            {
                                this.onFinish();
                            }
                }

                public void onFinish() {
                    MainAdapter adapter = new MainAdapter(getApplicationContext(), getObject());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    this.cancel();
                }
            }.start();

            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String emailx = dataSnapshot.getValue(String.class);
                    final String[] all=emailx.split(";");
                    for(int i=0;i<all.length;i++)
                    {

                       final DatabaseReference myRefx= database.getReference("user").child(all[i]);
                        final String[] total = {""};
                        final int finalI = i;
                        final int finalI1 = i;
                        final int finalI2 = i;
                        myRefx.child("totalads").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                total[0] = dataSnapshot.getValue(String.class);
                                final int ghj=Integer.parseInt(total[0]);
                                for(int j=ghj-1;j>=0;j--)
                                {
                                    fori.add(finalI2+"");
                                    forj.add(j+1+"");
                                    myRefx.child("ads"+(j+1)).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            nameall.add(dataSnapshot.getValue(String.class));

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });

                                    myRefx.child("ads"+(j+1)).child("prize").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            prizeall.add(dataSnapshot.getValue(String.class));

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });

                                    myRefx.child("ads"+(j+1)).child("squarefoor").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            squarefootall.add(dataSnapshot.getValue(String.class));

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });


                                    myRefx.child("ads"+(j+1)).child("rentorsell").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            rentall.add(dataSnapshot.getValue(String.class));

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });

                                    final int finalJ = j;
                                     myRefx.child("ads"+(j+1)).child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            imageall.add(dataSnapshot.getValue(String.class));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });

                                    if (finalI1 ==all.length-1 && finalJ ==0)
                                    {
                                        myref1.child("check").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                check=dataSnapshot.getValue(String.class);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                            }
                                        });

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                            }
                        });



                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });

            return null;
        }
    }
}