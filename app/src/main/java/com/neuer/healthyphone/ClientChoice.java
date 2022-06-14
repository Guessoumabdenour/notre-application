package com.neuer.healthyphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClientChoice extends AppCompatActivity {


    private FirebaseAuth mAuth;
    Button button;
    ProgressBar progressBar;
    EditText EMail,Pass;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(ClientChoice.this, Decision.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_choice);

        mAuth = FirebaseAuth.getInstance();
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.Progress);
        EMail = findViewById(R.id.Mail);
        Pass = findViewById(R.id.Pass);
    }

    public void Login(View view) {
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String Mail = EMail.getText().toString().trim().toLowerCase();
        String Password = Pass.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(Mail).matches()){
            Toast.makeText(ClientChoice.this, ("incorrect email form "), Toast.LENGTH_SHORT).show();
            button.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        else {
            mAuth.signInWithEmailAndPassword(Mail, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Connected successfully", Toast.LENGTH_SHORT).show();
                        button.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(ClientChoice.this, Decision.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "please verify your informations", Toast.LENGTH_SHORT).show();
                        button.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    public void SignUp(View view) {
        Intent intent = new Intent(ClientChoice.this,SigninClient.class);
        startActivity(intent);
    }
}