package com.lost_n_found.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.lost_n_found.R;

public class PostDetails extends AppCompatActivity {
     ImageView postImgView;
     TextView dateT,desT,contactT,locationT,titleT;
     Chip chip ;
     ImageButton bckBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        postImgView = findViewById(R.id.postImageDetails);
        dateT = findViewById(R.id.dateDetails);
        locationT = findViewById(R.id.LocationDetail);
        desT = findViewById(R.id.descriptionDetails);
        contactT = findViewById(R.id.contactDetails);
        chip = findViewById(R.id.chipdetails);
        titleT = findViewById(R.id.detailsTitle);
        bckBtn = findViewById(R.id.backButtonTopPDetails);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String status = intent.getStringExtra("status");
        String date = intent.getStringExtra("date");
        String des = intent.getStringExtra("des");
        String location = intent.getStringExtra("location");
        String contact = intent.getStringExtra("contact");
        String name = intent.getStringExtra("name");
        String imgUrl = intent.getStringExtra("imgUrl");

        titleT.setText(title);
        dateT.setText(date);
        contactT.setText(contact);
        desT.setText(des);
        locationT.setText(location);
        chip.setText(name);
        Glide.with(getApplicationContext()).load(imgUrl).into(postImgView   );


        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

}