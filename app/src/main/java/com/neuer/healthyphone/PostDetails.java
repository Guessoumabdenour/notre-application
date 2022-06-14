package com.neuer.healthyphone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class PostDetails extends AppCompatActivity {

    TextView Name;
    Button Details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);


        Name = findViewById(R.id.Title);
        Details = findViewById(R.id.Details);


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
}