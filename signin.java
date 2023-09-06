package com.example.projcopy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;


public class signin extends Fragment {
    TextView createnewaccount;
    EditText username, password;
    Button login;
    String emailpattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    ProgressDialog progressDialog;
    private FirebaseAuth Auth;
    FirebaseUser mUser;
    private Button button;
    private FirebaseAuth firebaseAuth;



    public signin() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        createnewaccount = view.findViewById(R.id.register);
        username = view.findViewById(R.id.u1);
        password =view. findViewById(R.id.p1);
        login =view.findViewById(R.id.save_post_btn);
        progressDialog = new ProgressDialog(getContext());
        Auth = FirebaseAuth.getInstance();
        mUser = Auth.getCurrentUser();
        FirebaseFirestore db=FirebaseFirestore.getInstance();


        createnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity2.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_mail=username.getText().toString();
                String txt_password=password.getText().toString();
                perforlogin(txt_mail,txt_password);
            }
        });

        return view;
    }
    private void perforlogin(String mail, String pass) {
        if (!mail.isEmpty() && !pass.isEmpty()){
            Auth.signInWithEmailAndPassword(mail , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Login Successfull !!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext() , HomeActivity.class));

                    }else{
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
        }

    }
}