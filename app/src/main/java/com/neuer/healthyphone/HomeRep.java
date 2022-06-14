package com.neuer.healthyphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeRep extends AppCompatActivity {

    Button out,score,mail,level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_rep);


        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        score = findViewById(R.id.score);
        out = findViewById(R.id.out);
        mail = findViewById(R.id.mail);
        mail.setText(user.getEmail());
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });

        // Inflate the layout for this fragment
        score.setText(user.getDisplayName());
    }
    public void SignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeRep.this,ClientChoice.class);
        startActivity(intent);
        finish();
    }
    public void Orders(View view) {
        Intent intent = new Intent(HomeRep.this,Orders.class);
        startActivity(intent);
    }

    public void Posts(View view) {
        Intent intent = new Intent(HomeRep.this,AddPost.class);
        startActivity(intent);
    }
}