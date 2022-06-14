package com.neuer.healthyphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SigninClient extends AppCompatActivity {

    EditText User,Mail,Pass;
    FirebaseAuth mAuth;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_client);
        User = findViewById(R.id.User);
        Mail = findViewById(R.id.Mail);
        Pass = findViewById(R.id.Pass);
        mAuth = FirebaseAuth.getInstance();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

    }

    public void Save(View view) {
        selectedRadioButton  = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        //get RadioButton text
        String yourVote = selectedRadioButton.getText().toString();

        String E, P;
        E = Mail.getText().toString().trim();
        P = Pass.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(E).matches()) {
            Toast.makeText(SigninClient.this, ("please enter your informations correctly"), Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(E, P)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                int A = 0;
                                Intent intent = new Intent(SigninClient.this, HomeClient.class);

                                if(yourVote.equals("Client")){
                                        A = 0;
                                }
                                else {
                                        A = 1;
                                    intent = new Intent(SigninClient.this, CreateBoutique.class);
                                }
                                UserProfileChangeRequest profileUpdates =
                                        new UserProfileChangeRequest.Builder().setDisplayName(User.getText().toString()).setPhotoUri(Uri.parse(String.valueOf(A))).build();
                                user.updateProfile(profileUpdates);
                                Intent finalIntent = intent;
                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SigninClient.this, "Account Created Succesfuly", Toast.LENGTH_SHORT).show();

                                            startActivity(finalIntent);
                                            finish();
                                        } else
                                            Toast.makeText(SigninClient.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SigninClient.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}