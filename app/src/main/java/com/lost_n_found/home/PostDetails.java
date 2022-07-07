package com.lost_n_found.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.lost_n_found.BlurTransformation;
import com.lost_n_found.R;
import com.lost_n_found.home.chatMessages.chatActivity;

public class PostDetails extends AppCompatActivity {
    ImageView postImgView;
    TextView dateT, desT, contactT, locationT, titleT;
    Chip chip;
    ImageButton bckBtn;
    Button helpBtn;
    ShimmerFrameLayout shimmerFrameLayout;

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
        helpBtn = findViewById(R.id.helpBtnDetails);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String status = intent.getStringExtra("status");
        String date = intent.getStringExtra("date");
        String des = intent.getStringExtra("des");
        String location = intent.getStringExtra("location");
        String contact = intent.getStringExtra("contact");

        String name = intent.getStringExtra("name");
        String imgUrl = intent.getStringExtra("imgUrl");
        String btnTitle = intent.getStringExtra("btnTitle");
        String postuid = intent.getStringExtra("postuid");
        String avatar = intent.getStringExtra("avatar");
        String []nameArr = name.split(":");


        titleT.setText(title);
        dateT.setText(date);
        contactT.setText(contact);
        desT.setText(des);
        locationT.setText(location);
        chip.setText(name);
        if (imgUrl.contains("nodataavailable") || btnTitle.equals("mypost")){
            Glide.with(this).load(imgUrl).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    shimmerFrameLayout.stopShimmerAnimation();
                    return false;
                }
            }).into(postImgView);
        }
        else {
        Glide.with(this)
                .asBitmap()
                .load(imgUrl) // or url
                .transform(new BlurTransformation(this)).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        shimmerFrameLayout.stopShimmerAnimation();
                        return false;
                    }
                })
                .into(postImgView);}

        if (!btnTitle.equals("mypost") && !postuid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
            helpBtn.setText(btnTitle);
            helpBtn.setVisibility(View.VISIBLE);
        }


        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), chatActivity.class);
                intent.putExtra("name", nameArr[1].trim());
                intent.putExtra("avatar", avatar);
                intent.putExtra("uid",postuid);
                startActivity(intent);


            }
        });


        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

}