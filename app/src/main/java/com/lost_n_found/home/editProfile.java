package com.lost_n_found.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lost_n_found.R;

import java.io.FileNotFoundException;

public class editProfile extends AppCompatActivity {

    TextView imgTxt;
    ImageButton camIcon , deleteIcon ,backBtn;
    ImageView pfileImg;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //widgets
        imgTxt = findViewById(R.id.imgtxtPfile);
        deleteIcon = findViewById(R.id.deleteIconPfile);
        backBtn = findViewById(R.id.backButtonTopPfile);
        pfileImg = findViewById(R.id.pfileImage);
        camIcon = findViewById(R.id.camIconPfile);

        camIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postImg.setVisibility(View.VISIBLE);
//                deleteIcon.setVisibility(View.VISIBLE);
//                camIcon.setVisibility(View.GONE);
//                imgTxt.setVisibility(View.GONE);

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                try {
                    startActivityForResult(intent,0);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                    Toast.makeText(getApplicationContext(),"Somethin went wrong",Toast.LENGTH_SHORT).show();
                }


            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pfileImg.setVisibility(View.GONE);
                deleteIcon.setVisibility(View.GONE);
                camIcon.setVisibility(View.VISIBLE);
                imgTxt.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();


            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));

                pfileImg.setImageBitmap(bitmap);
                pfileImg.setVisibility(View.VISIBLE);
                deleteIcon.setVisibility(View.VISIBLE);
                camIcon.setVisibility(View.GONE);
                imgTxt.setVisibility(View.GONE);
            }
            catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}