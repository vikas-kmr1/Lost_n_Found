package com.lost_n_found.login;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lost_n_found.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login_Signup_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_Signup_Fragment extends Fragment {
    Resources signinUsing;
    private FirebaseAuth mAuth;
    private DatabaseReference mDbRef;
    private EditText email;
    private EditText fullname;
    private EditText password;
    private EditText confirm_password;
    private Button signup;
    LottieAnimationView lottieAnimationView ;
    TextView verifyTExt;
    CardView cardView ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String gender;
    private String regex = "^(.+)@(.+)$";


    public Login_Signup_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_Signup_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_Signup_Fragment newInstance(String param1, String param2) {
        Login_Signup_Fragment fragment = new Login_Signup_Fragment();
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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // reload();
        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login__signup_, container, false);


        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        fullname = root.findViewById(R.id.fullname);
        confirm_password = root.findViewById(R.id.pass_confirm);
        signup = root.findViewById(R.id.sign_btn);
        RadioGroup genderGroup = root.findViewById(R.id.genderRadios);

        lottieAnimationView = root.findViewById(R.id.tick_animation);
        verifyTExt = root.findViewById(R.id.verifytext);
        cardView = root.findViewById(R.id.ticklayout);


        //login authentication

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_user = email.getText().toString();
                String pass_user = password.getText().toString();
                String full_n = fullname.getText().toString();
                String c_pass = confirm_password.getText().toString();

                try {

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email_user);

                    if (email_user.isEmpty()) {
                        email.setError("This field can't be empty!");
                    }


                    if ((!matcher.matches() || !email_user.endsWith(".com")) && !email_user.isEmpty()) {
                        Toast.makeText(getContext(), "Enter valid Email!", Toast.LENGTH_SHORT).show();
                    }

                    if (pass_user.isEmpty()) {
                        password.setError("This field can't be empty!");
                    }

                    if (c_pass.isEmpty()) {
                        confirm_password.setError("This field can't be empty!");
                    }

                    if (full_n.isEmpty()) {
                        fullname.setError("This field can't be empty!");
                    }

                    else if (!pass_user.equals(c_pass))  {
                        Toast.makeText(getContext(), "Password not matched!", Toast.LENGTH_SHORT).show();
                    }

                    else if (genderGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getContext(), "Choose Your Pronouns!", Toast.LENGTH_SHORT).show();
                    }

//                    else if(!Pattern.matches("[a-zA-Z0-9]", pass_user)){
//                        Toast.makeText(getContext() ,"Password should be a combination of numbers and alphabets!", Toast.LENGTH_SHORT).show();
//                    }

                    else if (pass_user.length() < 8 || pass_user.length() > 20) {
                        Toast.makeText(getContext(), "Password should be of length (8 - 20)!", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        RadioButton radioMale = root.findViewById(R.id.male);
                        RadioButton radioFemale = root.findViewById(R.id.female);
                        if (radioMale.isChecked()) {
                            gender = "male";
                        } else if (radioFemale.isChecked()) {
                            gender = "female";
                        }

                        // Toast.makeText(getContext(), ""+gender, Toast.LENGTH_SHORT).show();
                        SignUpFun(email_user, pass_user, fullname.getText().toString());
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return root;

    }

    private void SignUpFun(String email_user, String pass_user, String user_name) {
        final String email = email_user;
        final String password = pass_user;

        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "com.example.android",
                                true, /* installIfNotAvailable */
                                "08"    /* minimumVersion */)
                        .build();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {

                            FirebaseUser user = mAuth.getCurrentUser();
                            ProgressDialog progressDialog = new ProgressDialog(requireActivity());
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("creating new account...");
                            progressDialog.show();
                            //sending verification email:
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if(progressDialog.isShowing()){
                                        progressDialog.dismiss();
                                    }
                                    signup.setVisibility(View.GONE);
                                    cardView.setVisibility(View.VISIBLE);
                                    lottieAnimationView.playAnimation();

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(requireContext(), "Check your inbox", Toast.LENGTH_LONG).show();
                                        }
                                    }, 5000);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();

                                }
                            });





                            if (task.isSuccessful()) {



                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signup successfully", "createUserWithEmail:success");


                                addUserToDatabase(user_name, email, mAuth.getCurrentUser().getUid());
//                                Intent intent = new Intent(getActivity(), home.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                requireActivity().finish();
                                //TODO Progressbar


//                                startActivity(intent);
//                                requireActivity().finish();
                                //updateUI(user);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Signup failed", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Authentication failed or user already exist!",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }


    private void addUserToDatabase(String username, String useremail, String uid) {
        mDbRef = FirebaseDatabase.getInstance().getReference();
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child("avataar");
        storageReference.child(gender+".gif").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
            // Toast.makeText(getContext(),uri+"",Toast.LENGTH_LONG).show();
                mDbRef.child("user").child(uid).setValue(new CreateUser(uid.trim(), username.trim(), useremail.trim(), uri+""));

            }
        });

    }
}