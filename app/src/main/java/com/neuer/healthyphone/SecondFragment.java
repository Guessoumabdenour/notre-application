package com.neuer.healthyphone;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SecondFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    EditText editText,Min,Max;
    RecyclerView recyclerView;
    BoutiqueAD postAd;
    List<Boutique> postList,mClient;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Spinner spinner;
    int MaxPrice=Integer.MAX_VALUE,MinPrice=0;
    String Wilaya="",Word="";

    @Override
    public void onStart() {
        super.onStart();

        postList = new ArrayList<>();
        mClient = new ArrayList<>();

        postAd = new BoutiqueAD();

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Boutique");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    for (DataSnapshot postsnap1 : postsnap.getChildren()) {

                        Boutique post = postsnap1.getValue(Boutique.class);
                        postList.add(post);
                        mClient.add(post);

                    }
                }
                postAd = new BoutiqueAD(getActivity(), mClient);
                recyclerView.setAdapter(postAd);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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

        View v = inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = v.findViewById(R.id.view);
        spinner = v.findViewById(R.id.Wilaya);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.wilayaS, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        editText = v.findViewById(R.id.Search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Word = s.toString();
                mClient = new ArrayList<>();
                mClient.clear();
                ArrayList<Boutique> arrayList3 = filter();
                mClient.addAll(arrayList3);
                postAd.notifyDataSetChanged();
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseDatabase = FirebaseDatabase.getInstance();


        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0) {
            Wilaya = parent.getItemAtPosition(position).toString();
            mClient.clear();
            ArrayList<Boutique> arrayList3 = filter();
            mClient.addAll(arrayList3);
            postAd.notifyDataSetChanged();
        }
        else {
            Wilaya = "";
            mClient.clear();
            ArrayList<Boutique> arrayList3 = filter();
            mClient.addAll(arrayList3);
            postAd.notifyDataSetChanged();
        }



        // Toast.makeText(parent.getContext(),text,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        //text = "Adrar";
    }

    private ArrayList<Boutique> filter(){
        ArrayList<Boutique> FiltredList = new ArrayList<>();
        for(Boutique item : postList){
            if(item.getName().toLowerCase().contains(Word.toLowerCase())){
                FiltredList.add(item);
            }
        }
        ArrayList<Boutique> FiltredList2 = new ArrayList<>();

        for(Boutique item : FiltredList){
            if(item.getWilaya().toLowerCase().contains(Wilaya.toLowerCase())){
                FiltredList2.add(item);
            }
        }


        return  FiltredList2;
    }
}