package com.neuer.healthyphone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {


    EditText editText,Min,Max;
    RecyclerView recyclerView;
    AskAD postAd;
    List<Ask> postList,mClient;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Spinner spinner;
    int MaxPrice=Integer.MAX_VALUE,MinPrice=0;
    String Wilaya="",Word="";

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

        databaseReference = firebaseDatabase.getReference().child("Asks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    for (DataSnapshot postsnap1 : postsnap.getChildren()) {

                        Ask post = postsnap1.getValue(Ask.class);
                        if(post.getClientID() != null)
                        if(post.getClientID().equals(user.getUid()))
                            mClient.add(post);

                    }
                }
                postAd = new AskAD(getActivity(), mClient);
                recyclerView.setAdapter(postAd);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public ThirdFragment() {
        // Required empty public constructor
    }



    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_third, container, false);

        recyclerView = v.findViewById(R.id.view);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseDatabase = FirebaseDatabase.getInstance();


        return v;
    }
}