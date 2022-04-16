package com.lost_n_found.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link login_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class login_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

     private EditText email;
    private EditText password;
    private TextView forget;
    private Button login;

    private FirebaseAuth mAuth;
// ...


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public login_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static login_fragment newInstance(String param1, String param2) {
        login_fragment fragment = new login_fragment();
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





    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_login_fragment,container,false);

        EditText email=root.findViewById(R.id.email);
        EditText password=root.findViewById(R.id.pass);
        TextView forget=root.findViewById(R.id.forget_pass);
        Button login =root.findViewById(R.id.lg_btn);

        email.setTranslationX(300);
        password.setTranslationX(300);
        forget.setTranslationX(300);
        login.setTranslationX(300);

        email.setAlpha(0);
        password.setAlpha(0);
        forget.setAlpha(0);
        login.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(400).start();
        password.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(600).start();
        forget.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(800).start();
        login.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(800).start();



        //login authentication
        mAuth = FirebaseAuth.getInstance();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_user = email.getText().toString();
                String pass_user = password.getText().toString();

                loginFun(email_user,pass_user);
            }
        });





        //TODO call after succesful login
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent= new Intent(getActivity(), home.class);
//                startActivity(intent);
//
//            }
//        });

        return root;



    }

    private void loginFun(String email_user, String pass_user) {


        mAuth.signInWithEmailAndPassword(email_user, pass_user)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginSuccessed", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent= new Intent(getActivity(), home.class);
                            startActivity(intent);
                            Toast.makeText(getContext(), "Welcome Back!.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginDeclined", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //  updateUI(null);
                        }
                    }
                });

    }


}