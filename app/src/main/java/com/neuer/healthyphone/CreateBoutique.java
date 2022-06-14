package com.neuer.healthyphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateBoutique extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String text;
    EditText Name,City,Phone;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_boutique);
        spinner = findViewById(R.id.Wilaya);
        Name = findViewById(R.id.Name);
        City = findViewById(R.id.City);
        Phone = findViewById(R.id.Phone);


        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Boutique");



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.wilaya, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        // Toast.makeText(parent.getContext(),text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        text = "Annaba";
    }

    public void Save(View view) {
        FirebaseUser CurrentUser = mAuth.getCurrentUser();
        String Key = databaseReference.push().getKey();

        Boutique boutique = new Boutique();
        boutique.setUID(CurrentUser.getUid());
        boutique.setWilaya(text);
        boutique.setCity(City.getText().toString());
        boutique.setName(Name.getText().toString());
        boutique.setPhone(Phone.getText().toString());

        boutique.setKey(Key);

        databaseReference.child(CurrentUser.getUid()).child(Key).setValue(boutique);

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(String.valueOf(2))).build();
        CurrentUser.updateProfile(profileUpdates);
        CurrentUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateBoutique.this, "Account Created Succesfuly", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CreateBoutique.this, HomeRep.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(CreateBoutique.this, "Failed to create boutique" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}