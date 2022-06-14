package com.neuer.healthyphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {



    RecyclerView recyclerView;
    AskAD postAd;
    List<Ask> postList,mClient;
    FirebaseDatabase firebaseDatabase;



    @Override
    public void onStart() {
        super.onStart();

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        FirebaseAuth mAuth;
        FirebaseUser user ;

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        postList = new ArrayList<>();
        mClient = new ArrayList<>();

        postAd = new AskAD();

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Asks").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {

                        Ask post = postsnap.getValue(Ask.class);
                            mClient.add(post);


                }
                postAd = new AskAD(Orders.this, mClient);
                recyclerView.setAdapter(postAd);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Orders.this));
        firebaseDatabase = FirebaseDatabase.getInstance();

    }
}