package com.lost_n_found.home;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lost_n_found.R;

import java.util.Random;

public class editProfile extends AppCompatActivity {
    EditText first_n,last_n,mobile_n,enroll_n;
    TextView imgTxt;
    ImageButton camIcon , deleteIcon ,backBtn;
    ImageView pfileImg;
    Button saveBtn;
    Uri targetUri=null;
    LottieAnimationView lottieAnimationView ;
    TextView verifyTExt;
    CardView cardView ;
    ProgressDialog progressDialog;

    String f_name ;
    String l_name;
    String enrollment ;
    String mobile;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        //

        Intent intent = getIntent();
        f_name = intent.getStringExtra("f_name");
        l_name = intent.getStringExtra("l_name");
         enrollment = intent.getStringExtra("enrollment");
         mobile = intent.getStringExtra("mobile");


        //widgets
        imgTxt = findViewById(R.id.imgtxtPfile);
        deleteIcon = findViewById(R.id.deleteIconPfile);
        backBtn = findViewById(R.id.backButtonTopPfile);
        saveBtn = findViewById(R.id.editBtn);
        pfileImg = findViewById(R.id.pfileImage);
        camIcon = findViewById(R.id.camIconPfile);

        first_n = findViewById(R.id.f_name);
        last_n = findViewById(R.id.l_name);
        enroll_n = findViewById(R.id.enroll_n);
        mobile_n = findViewById(R.id.mobile_n);

        cardView = findViewById(R.id.ticklayoutEdit);
        verifyTExt = findViewById(R.id.verifytextEdit);
        lottieAnimationView = findViewById(R.id.tick_animationEdit);

        first_n.setHint(f_name);
        last_n.setHint(l_name);
        enroll_n.setHint(enrollment);
        mobile_n.setHint(mobile );
        progressDialog = new ProgressDialog(editProfile.this);



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
                targetUri = null;
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser();

            }
        });




    }

    public void editUser(){


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child("avataar");
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Updating...");
        progressDialog.show();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DatabaseReference databaseReferenceUser = firebaseDatabase.getReference("user/"+uid);
        DatabaseReference databaseReference = firebaseDatabase.getReference("posts");


        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!TextUtils.isEmpty(first_n.getText()))
                {
                    databaseReferenceUser.child("username").setValue(first_n.getText().toString()+" "+last_n.getText().toString());
                }

                if(!TextUtils.isEmpty(enroll_n.getText()))
                {
                    if(enroll_n.getText().length()==12) {
                        databaseReferenceUser.child("enrollment_no").setValue(enroll_n.getText() + "".toUpperCase());
                    }

                    else
                    {
                        enroll_n.setError("Enter Valid Enrollment");
                    }

                    }

                if(!TextUtils.isEmpty(mobile_n.getText()))
                {
                    if(mobile_n.getText().length() ==10) {
                        databaseReferenceUser.child("phone").setValue(mobile_n.getText() + "".toUpperCase());
                    }
                     else
                    {
                        mobile_n.setError("Enter Valid mobile no,");
                    }

                }
                String name = first_n.getHint()+"";
                if(!TextUtils.isEmpty(first_n.getText()) && TextUtils.isEmpty(last_n.getText()) )
                {
                    name = first_n.getText()+" "+last_n.getHint();
                    databaseReferenceUser.child("username").setValue(name);
                }

                if(TextUtils.isEmpty(first_n.getText()) && !TextUtils.isEmpty(last_n.getText()) )
                {
                    name = first_n.getHint()+" "+last_n.getText();
                    databaseReferenceUser.child("username").setValue(name);
                }

                if(!TextUtils.isEmpty(first_n.getText()) && !TextUtils.isEmpty(last_n.getText()) )
                {
                    name = first_n.getText()+" "+last_n.getText();
                    databaseReferenceUser.child("username").setValue(name);
                }

                final Handler handler = new Handler();

                if(targetUri==null){

                    handler.postDelayed(() -> {

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();}
                        saveBtn.setVisibility(View.GONE);
                        cardView.setVisibility(View.VISIBLE);
                        lottieAnimationView.playAnimation();
                    }, 1500);

                    try {
                        handler.postDelayed(() -> {

                            finish();
                        }, 3000);
                    }catch (Exception e){

                    }



                }

                if (targetUri!=null)
                {
                    try{

                        String imgStr = name + (new Random().nextInt(1000) );

                        storageReference.child(imgStr).putFile(targetUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                storageReference.child(imgStr).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        databaseReferenceUser.child("avatar").setValue(uri.toString());

                                        if(progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                        saveBtn.setVisibility(View.GONE);
                                        cardView.setVisibility(View.VISIBLE);
                                        lottieAnimationView.playAnimation();

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Do something after 5s = 5000ms
                                                finish();
                                            }
                                        }, 6000);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(editProfile.this, ""+"failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    }
                    catch (Exception e){

                    }


                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
               // bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));

               // pfileImg.setImageBitmap(bitmap);
                Glide.with(getApplicationContext()).load(targetUri).into(pfileImg);
                pfileImg.setVisibility(View.VISIBLE);
                deleteIcon.setVisibility(View.VISIBLE);
                camIcon.setVisibility(View.GONE);
                imgTxt.setVisibility(View.GONE);
            }
            catch (Exception  e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}