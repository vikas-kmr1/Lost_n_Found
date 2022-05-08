package com.lost_n_found.home;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lost_n_found.R;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class NewPost extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText dateText;
    SimpleDateFormat dateFormat;
    String myFormat;
    TextView imgTxt;
    ImageButton camIcon , deleteIcon,backBtn;
    Button postBtn;
    ImageView postImg;
    EditText titleText;
    EditText descripText;
    EditText locationText;
    EditText contactText;
    RadioGroup radioGroup;
    RadioButton lostRBtn, foundRBtn;
    ConstraintLayout constraintLayout;
    LinearLayout linearLayoutimage;
    LinearLayout linearLayoutdetails;
    LottieAnimationView lottieAnimationView;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private Uri targetUri = null;
    String title,description,location,dateString,status = null,contact;
    private ProgressDialog progressDialog;

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
        titleText = findViewById(R.id.titleText);
        descripText = findViewById(R.id.descriptionText);
        locationText = findViewById(R.id.LocationText);
        contactText = findViewById(R.id.contactText);
        postBtn = findViewById(R.id.postBtn);
        radioGroup = findViewById(R.id.radio_Gp);
        lostRBtn = findViewById(R.id.radioButtonLost);
        foundRBtn = findViewById(R.id.radioButtonFound);
        constraintLayout= findViewById(R.id.animationLayout);
        linearLayoutimage= findViewById(R.id.imageEditView);
        linearLayoutdetails= findViewById(R.id.detailsLayout);
        lottieAnimationView = findViewById(R.id.success_animation);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("posts");
        progressDialog = new ProgressDialog(NewPost.this);
        progressDialog.setCancelable(false);

        //browsing image whem clicked on cam icon
        camIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postImg.setVisibility(View*.VISIBLE);
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
                targetUri = null;
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
               // initializing the date picker
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewPost.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));

               // limities the date picker
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime()); // max date set as Today
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - Long.parseLong("47304051840") );// min date range set as past 18 months .

                datePickerDialog.show();


            }
        });


        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleText.getText().toString();
                description = descripText.getText().toString();
                location = locationText.getText().toString();
                contact = contactText.getText().toString();
                dateString = dateText.getText().toString();


                if(title.isEmpty()){
                    titleText.setError("This field can't be empty.");
                }

                if(location.isEmpty()){
                    locationText.setError("This field can't be empty.");
                }

                if(description.isEmpty()){
                    descripText.setError("This field can't be empty.");
                }

                if(contact.isEmpty()){
                    contact ="N/A";
                }

                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(NewPost.this, "Choose one option. (lost or found )", Toast.LENGTH_SHORT).show();
                }

                else if(radioGroup.getCheckedRadioButtonId() != -1 && !description.isEmpty() && !location.isEmpty() && !title.isEmpty()){

                    progressDialog.setMessage("uploading");
                    progressDialog.show();

                    if (lostRBtn.isChecked()){
                        status="lost";
                    }
                    if (foundRBtn.isChecked()){
                        status="found";
                    }


                    if (targetUri != null)

                   {

                       try{

                           String imgStr = title + (new Random().nextInt(1000) );

                           storageReference.child(imgStr).putFile(targetUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                   storageReference.child(imgStr).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {
                                           String m = uri.getLastPathSegment().toString();
                                           createPost(title,description,location,contact,dateString,status,m);
                                       }
                                   }).addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(NewPost.this, ""+"failed", Toast.LENGTH_SHORT).show();
                                       }
                                   });
                               }
                           });

                       }
                       catch (Exception e){

                       }
                   }
                    else {

                            createPost(title,description,location,contact,dateString,status,"N/A");

                    }


                }




            }
        });

    }

    private void createPost(String title, String description, String location, String contact, String dateStr , String statusStr,String imgUrl) {
       String uid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("posts").child(uid).push().setValue(new CreatePost(statusStr,title,description,location,dateStr,contact,imgUrl)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
             progressDialog.dismiss();
             constraintLayout.setVisibility(View.VISIBLE);
             constraintLayout.bringToFront();
             lottieAnimationView.playAnimation();
             postBtn.setVisibility(View.GONE);
             linearLayoutdetails.setVisibility(View.GONE);
             linearLayoutimage.setVisibility(View.GONE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            targetUri = data.getData();

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