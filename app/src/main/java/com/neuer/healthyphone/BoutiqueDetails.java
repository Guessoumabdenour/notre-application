package com.neuer.healthyphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BoutiqueDetails extends AppCompatActivity {

    TextView Name;
    EditText Details;
    ImageView Add;
    Button WT,Phone,Send;
    String upToNCharacters;
    private static final int REQUEST_CALL=1;
    Dialog Pop;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    ArrayList<String> URList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_details);

        PopUp();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Asks");

        Name = findViewById(R.id.Name);
        WT = findViewById(R.id.Wilaya);
        Phone = findViewById(R.id.Phone);

        Name.setText(getIntent().getExtras().getString("Name"));
        WT.setText(getIntent().getExtras().getString("Wilaya")+" / "+getIntent().getExtras().getString("City"));
        Phone.setText(getIntent().getExtras().getString("Phone"));
    }

    private void MakePhoneCall(String upToNCharacters) {
        String dial = "tel:" + upToNCharacters;
        if(ContextCompat.checkSelfPermission(BoutiqueDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(BoutiqueDetails.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MakePhoneCall(upToNCharacters);
            }
        }
    }

    public void Call(View view) {
        upToNCharacters = getIntent().getExtras().getString("Phone");
        MakePhoneCall(upToNCharacters);
    }

    public void Ask(View view) {
        Pop.show();
    }

    void PopUp(){
        Pop = new Dialog(BoutiqueDetails.this);
        Pop.setContentView(R.layout.popup);
        Pop.getWindow().setBackgroundDrawable(null);
        Pop.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        Pop.getWindow().getAttributes().gravity = Gravity.BOTTOM;

        Send = Pop.findViewById(R.id.Send);
        Add = Pop.findViewById(R.id.Add);
        Details = Pop.findViewById(R.id.Details);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    FirebaseAuth mAuth;
                    FirebaseUser user ;
                    mAuth = FirebaseAuth.getInstance();
                    user = mAuth.getCurrentUser();
                    if (Details.getText().toString().length() < 20) {
                        Toast.makeText(BoutiqueDetails.this, ("Please enter more details "), Toast.LENGTH_SHORT).show();
                    }
                    else if(FilePathUri == null) {

                        Toast.makeText(BoutiqueDetails.this, "Please enter a picture ", Toast.LENGTH_LONG).show();

                    }
                    else  {

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();

                        Ask imageUploadInfo = new Ask();

                        imageUploadInfo.setQuestion(Details.getText().toString());
                        imageUploadInfo.setDate(formatter.format(date).toString());
                        imageUploadInfo.setClientID(user.getUid());
                        imageUploadInfo.setClientName(user.getDisplayName());

                        imageUploadInfo.setBoutiqueID(getIntent().getExtras().getString("Key"));

                        int i=0;
                        String ImageUploadId = databaseReference.child(getIntent().getExtras().getString("Key")).push().getKey();
                        imageUploadInfo.setKey(ImageUploadId);

                        for(int Now=0;Now<ImageList.size();Now++){
                            Uri Single = ImageList.get(Now);
                            final StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(Single));

                            int finalNow = Now;
                            storageReference2.putFile(Single).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            String url = uri.toString();
                                            // Toast.makeText(AddCar.this, (url), Toast.LENGTH_SHORT).show();

                                            URList.add(url);

                                            if(finalNow ==0) {
                                                imageUploadInfo.setImage(url);
                                                databaseReference.child(user.getUid()).child(ImageUploadId).child("image")
                                                        .setValue(url);
                                            }
                                            if(finalNow ==1) {
                                                imageUploadInfo.setImage2(url);
                                                databaseReference.child(user.getUid()).child(ImageUploadId).child("image2")
                                                        .setValue(url);
                                            }
                                            if(finalNow ==2) {
                                                imageUploadInfo.setImage3(url);
                                                databaseReference.child(user.getUid()).child(ImageUploadId).child("image3")
                                                        .setValue(url);
                                            }
                                            if(finalNow ==3) {
                                                imageUploadInfo.setImage4(url);
                                                databaseReference.child(user.getUid()).child(ImageUploadId).child("image4")
                                                        .setValue(url);
                                            }
                                            if(finalNow ==4) {
                                                imageUploadInfo.setImage5(url);
                                                databaseReference.child(user.getUid()).child(ImageUploadId).child("image5")
                                                        .setValue(url);
                                            }
                                            if(finalNow ==5) {
                                                imageUploadInfo.setImage6(url);
                                                databaseReference.child(user.getUid()).child(ImageUploadId).child("image6")
                                                        .setValue(url);
                                            }
                                            if(finalNow ==6) {
                                                imageUploadInfo.setImage7(url);
                                                databaseReference.child(user.getUid()).child(ImageUploadId).child("image7")
                                                        .setValue(url);
                                            }
                                        }
                                    });

                                }
                            });

                        }


                        databaseReference.child(getIntent().getExtras().getString("Key")).child(ImageUploadId).setValue(imageUploadInfo);

                        Toast.makeText(getApplicationContext(), "Sent sucessfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BoutiqueDetails.this, HomeClient.class);
                        startActivity(intent);
                        finish();
                    }



            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                    startActivityForResult(intent, 7);


            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getClipData() != null) {


            int Count = data.getClipData().getItemCount();
            if(Count > 7){
                Toast.makeText(getApplicationContext(), "you can not enter more than 7 pictures", Toast.LENGTH_LONG).show();

            }
            else {

                int Current = 0;

                while (Current < Count) {

                    FilePathUri = data.getClipData().getItemAt(Current).getUri();
                    ImageList.add(FilePathUri);
                    Current++;


                }

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),  FilePathUri);
                    Add.setImageBitmap(bitmap);
                } catch (IOException e) {

                    e.printStackTrace();
                }


            }

        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


}