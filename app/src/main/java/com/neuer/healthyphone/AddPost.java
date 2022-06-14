package com.neuer.healthyphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class AddPost extends AppCompatActivity {

    EditText Title,Details;
    ImageView imageView;
    Uri FilePathUri;

    StorageReference storageReference;    // instanciation de Bdd m3a les fichiers( images)//
    DatabaseReference databaseReference;  // lektiba //
    ArrayList<Uri> ImageList = new ArrayList<Uri>(); // images //
    ArrayList<String> URList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");

        Title = findViewById(R.id.Title);
        Details = findViewById(R.id.Details);
        imageView = findViewById(R.id.imageView);

    }

    public void Send(View view) {


        FirebaseAuth mAuth; // autentification //
        FirebaseUser user ;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (Details.getText().toString().length() < 5) {
            Toast.makeText(AddPost.this, ("please enter a title & details"), Toast.LENGTH_SHORT).show();
        }
        else if(FilePathUri == null) {

            Toast.makeText(AddPost.this, "Please insert a picture", Toast.LENGTH_LONG).show();

        }
        else  {

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            Post imageUploadInfo = new Post();

            imageUploadInfo.setDetails(Details.getText().toString());
            imageUploadInfo.setDate(formatter.format(date).toString());
            imageUploadInfo.setBoutiqueID(user.getUid());
            imageUploadInfo.setName(user.getDisplayName());
            imageUploadInfo.setTitle(Title.getText().toString());


            int i=0;
            String ImageUploadId = databaseReference.child(user.getUid()).push().getKey();
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


            databaseReference.child(user.getUid()).child(ImageUploadId).setValue(imageUploadInfo);

            Toast.makeText(getApplicationContext(), "تم رفع عنصرك بنجاح", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddPost.this, HomeRep.class);
            startActivity(intent);
            finish();
        }



    }





    public void LoadImage(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent, 7);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getClipData() != null) {


            int Count = data.getClipData().getItemCount();
            if(Count > 7){
                Toast.makeText(getApplicationContext(), "لا يمكنك اختيار اكثر من 7 صور", Toast.LENGTH_LONG).show();

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
                    imageView.setImageBitmap(bitmap);
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