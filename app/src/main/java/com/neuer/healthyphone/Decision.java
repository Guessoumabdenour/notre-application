package com.neuer.healthyphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Decision extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser CurrentUser = mAuth.getCurrentUser();
        String A = String.valueOf(CurrentUser.getPhotoUrl());
        if(A.equals("0")){
            Intent intent = new Intent(Decision.this, HomeClient.class);
            startActivity(intent);
            finish();
        }
        else if(A.equals("1")){
            Intent intent = new Intent(Decision.this, CreateBoutique.class);
            startActivity(intent);
            finish();

        }
        else{
            Intent intent = new Intent(Decision.this, HomeRep.class);
            startActivity(intent);
            finish();
        }

    }

}