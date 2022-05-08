package com.lost_n_found.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lost_n_found.R;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfielFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfielFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    public ImageView avatar;
    ProgressDialog progressDialog;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    public String f_name, l_name, enrollment, mobile;
    StorageReference storageRef;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Bitmap bitmap;

    public ProfielFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfielFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfielFragment newInstance(String param1, String param2) {
        ProfielFragment fragment = new ProfielFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profiel, container, false);
        ImageView editProfileimg = root.findViewById(R.id.editProfileimg);
        TextView editProfiletxt = root.findViewById(R.id.editProfiletxt);
        avatar = root.findViewById(R.id.avatarUser);
        TextView emailTop = root.findViewById(R.id.EmailView);
        TextView usernameTop = root.findViewById(R.id.userName);
        TextView enrollTop = root.findViewById(R.id.enrollView);
        TextView firstName = root.findViewById(R.id.firstNameView);
        TextView lastName = root.findViewById(R.id.lastNameView);
        TextView enrollBottom = root.findViewById(R.id.EnrView);
        TextView emailBottom = root.findViewById(R.id.emailViewBottom);
        TextView phoneBottom = root.findViewById(R.id.phoneViewBottom);


        //intialiazing firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        databaseReference = firebaseDatabase.getReference().child("user").child(uid);



        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        // Attach a listener to read the data at our posts reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                emailTop.setText(dataSnapshot.child("email").getValue(String.class));
                emailBottom.setText(dataSnapshot.child("email").getValue(String.class));
                String avatarUrl = dataSnapshot.child("avatar").getValue(String.class).toString();
                avatarUrl = avatarUrl.substring(35);
                setProfileDp(avatarUrl);

                String fullname = dataSnapshot.child("username").getValue(String.class);
                usernameTop.setText(fullname);

                String[] F_Lname = fullname.split(" ");
                f_name = F_Lname[0];
                l_name = F_Lname[1];
                firstName.setText(F_Lname[0]);
                lastName.setText(F_Lname[1]);

                enrollment = dataSnapshot.child("enrollment_no").getValue(String.class);
                enrollTop.setText(enrollment);

                enrollBottom.setText(dataSnapshot.child("enrollment_no").getValue(String.class));

                mobile = dataSnapshot.child("phone").getValue(String.class);
                phoneBottom.setText(mobile);

//                if(!emailBottom.getText().equals("N/A")){
//                    progressDialog.dismiss();
//                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });





        editProfileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditProf();
            }
        });

        editProfiletxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditProf();

            }
        });

        return root;
    }

    private void setProfileDp(String avatarUrl) {
        StorageReference httpsReference = firebaseStorage.getReference(avatarUrl+"");
        try {
            File localfile = File.createTempFile("avatar", "jpg");
            httpsReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                     bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    avatar.setImageBitmap(bitmap);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startEditProf() {
        Intent intent = new Intent(getContext(), editProfile.class);
        intent.putExtra("f_name", f_name);
        intent.putExtra("l_name", l_name);
        intent.putExtra("enrollment", enrollment);
        intent.putExtra("mobile", mobile);
        startActivity(intent);
    }


}