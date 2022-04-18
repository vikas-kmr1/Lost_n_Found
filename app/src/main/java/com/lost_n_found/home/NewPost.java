package com.lost_n_found.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lost_n_found.R;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class NewPost extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText dateText;
    SimpleDateFormat dateFormat;
    String myFormat;

    TextView imgTxt;
    ImageButton camIcon , deleteIcon,backBtn;
    ImageView postImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        String myFormat="dd/MM/yy";
        dateFormat = new SimpleDateFormat(myFormat, Locale.US);



        dateText=(EditText) findViewById(R.id.dateText);
        dateText.setText(dateFormat.format(myCalendar.getTime()));


        //widgets
        imgTxt = findViewById(R.id.imgtxtPost);
        deleteIcon = findViewById(R.id.deleteIconPost);
        backBtn = findViewById(R.id.backButtonTopPost);
        postImg = findViewById(R.id.postImage);
        camIcon = findViewById(R.id.camIconPost);

        //browsing image whem clicked on cam icon
        camIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postImg.setVisibility(View.VISIBLE);
//                deleteIcon.setVisibility(View.VISIBLE);
//                camIcon.setVisibility(View.GONE);
//                imgTxt.setVisibility(View.GONE);

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,0);

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
                postImg.setVisibility(View.GONE);
                deleteIcon.setVisibility(View.GONE);
                camIcon.setVisibility(View.VISIBLE);
                imgTxt.setVisibility(View.VISIBLE);
            }
        });


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                myCalendar.setTimeZone(TimeZone.getTimeZone("IST"));
                updateLabel();
            }
        };

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // initializing the date picker
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewPost.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));

               // limities the date picker
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis()); // max date set as Today
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis() - Long.parseLong("47304051840") );// min date range set as past 18 months .

                datePickerDialog.show();


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

                postImg.setImageBitmap(bitmap);
                postImg.setVisibility(View.VISIBLE);
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

    private void updateLabel(){
        dateText.setText(dateFormat.format(myCalendar.getTime()));
    }
}