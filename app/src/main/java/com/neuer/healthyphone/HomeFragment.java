package com.neuer.healthyphone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Button out,score,mail,level;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        score = v.findViewById(R.id.score);
        out = v.findViewById(R.id.out);
        mail = v.findViewById(R.id.mail);
        mail.setText(user.getEmail());
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });

        // Inflate the layout for this fragment
        score.setText(user.getDisplayName());

        return v;
    }

    public void SignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(),ClientChoice.class);
        startActivity(intent);
        getActivity().finish();
    }
}