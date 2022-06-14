package com.neuer.healthyphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AskDetails extends AppCompatActivity {

    TextView Name;
    Button Details;

    EditText Comment;

    RecyclerView recyclerView;
    CommentAd postAd;
    List<Comment> postList,mClient;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user ;


    @Override
    public void onStart() {
        super.onStart();


        postAd = new CommentAd();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Comments").child(getIntent().getExtras().getString("Key"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mClient = new ArrayList<>();


                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {

                        Comment post = postsnap.getValue(Comment.class);
                        mClient.add(post);


                }
                postAd = new CommentAd(AskDetails.this, mClient);
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
        setContentView(R.layout.activity_ask_details);


        recyclerView = findViewById(R.id.view);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AskDetails.this));
        firebaseDatabase = FirebaseDatabase.getInstance();



        Name = findViewById(R.id.Title);
        Details = findViewById(R.id.Details);

        Comment = findViewById(R.id.Comment);

        Name.setText(getIntent().getExtras().getString("Name"));
        Details.setText(getIntent().getExtras().getString("Details"));


        ImageSlider imageSlider = findViewById(R.id.image_slider);

        List<SlideModel> slideModelList = new ArrayList<>();

        if(!getIntent().getExtras().getString("Image").equals("")) {
            slideModelList.add(new SlideModel(getIntent().getExtras().getString("Image")));
        }
        if(!getIntent().getExtras().getString("Image2").equals("")) {
            slideModelList.add(new SlideModel(getIntent().getExtras().getString("Image2")));
        }
        if(!getIntent().getExtras().getString("Image3").equals("")) {
            slideModelList.add(new SlideModel(getIntent().getExtras().getString("Image3")));
        }
        if(!getIntent().getExtras().getString("Image4").equals("")) {
            slideModelList.add(new SlideModel(getIntent().getExtras().getString("Image4")));
        }
        if(!getIntent().getExtras().getString("Image5").equals("")) {
            slideModelList.add(new SlideModel(getIntent().getExtras().getString("Image5")));
        }
        if(!getIntent().getExtras().getString("Image6").equals("")) {
            slideModelList.add(new SlideModel(getIntent().getExtras().getString("Image6")));
        }
        if(!getIntent().getExtras().getString("Image7").equals("")) {
            slideModelList.add(new SlideModel(getIntent().getExtras().getString("Image7")));
        }

        imageSlider.setImageList(slideModelList,true);
    }

    public void AddComment(View view) {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference CommentRef = firebaseDatabase.getReference("Comments").child(getIntent().getExtras().getString("Key")).push();

        Comment comment = new Comment(Comment.getText().toString(),user.getUid(),user.getDisplayName());



        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        comment.setTime(formatter.format(date).toString());


        CommentRef.setValue(comment);

        Comment.setText("");

    }
}