package com.lost_n_found.login;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lost_n_found.R;
import com.lost_n_found.home.home;

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
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private Button signup;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private  String regex = "^(.+)@(.+)$";


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
        if(currentUser != null){
           // reload();
        }



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_login__signup_,container,false);
        

        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        confirm_password = root.findViewById(R.id.pass_confirm);
        signup = root.findViewById(R.id.sign_btn);
        

        //login authentication

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_user = email.getText().toString();
                String pass_user = password.getText().toString();
                String c_pass = confirm_password.getText().toString();

                try {

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email_user);

                    if (email_user.isEmpty()){
                        email.setError("This field can't be empty!");
                    }

                    if((!matcher.matches() || !email_user.endsWith(".com")) && !email_user.isEmpty()){
                        Toast.makeText(getContext() ,"Enter valid Email!", Toast.LENGTH_SHORT).show();
                    }

                    if(pass_user.isEmpty()){
                        password.setError("This field can't be empty!");
                    }

                    else if(!pass_user.equals(c_pass) && !c_pass.isEmpty()){
                        Toast.makeText(getContext() ,"Password not matched!", Toast.LENGTH_SHORT).show();
                    }

//                    else if(!Pattern.matches("[a-zA-Z0-9]", pass_user)){
//                        Toast.makeText(getContext() ,"Password should be a combination of numbers and alphabets!", Toast.LENGTH_SHORT).show();
//                    }

                    else if(pass_user.length() < 8 || pass_user.length()>20){
                        Toast.makeText(getContext() ,"Password sholud me of length (8 - 20)!", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        SignUpFun(email_user,pass_user);}

                }

                catch (Exception e){
                    Toast.makeText(getContext(),"Something Went Wrong!",Toast.LENGTH_SHORT).show();
                }


            }
        });





        return root;

    }

    private void SignUpFun( String email_user, String pass_user) {
        final String email = email_user;
        final String password  = pass_user;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signup successfully", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(getActivity(), home.class);
                                startActivity(intent);
//                                getActivity().finish();
                                Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Signup failed", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Authentication failed or user already exist!",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getContext() ,"Somethin went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}