package com.example.projcopy;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;


public class MainActivity extends AppCompatActivity {
    TextView createnewaccount;
    EditText username, password;
    Button login;
    String emailpattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    ProgressDialog progressDialog;
    private FirebaseAuth Auth;
    FirebaseUser mUser;
    private Button button;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createnewaccount = findViewById(R.id.register);
        username = findViewById(R.id.u1);
        password = findViewById(R.id.p1);
        login = findViewById(R.id.get);
        progressDialog = new ProgressDialog(this);
        Auth = FirebaseAuth.getInstance();
        mUser = Auth.getCurrentUser();
        FirebaseFirestore db=FirebaseFirestore.getInstance();


        createnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, MainActivity2.class));
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


    }



    private void perforlogin(String mail, String pass) {
        if (!mail.isEmpty() && !pass.isEmpty()){
            Auth.signInWithEmailAndPassword(mail , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Login Successfull !!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this , HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(MainActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
        }

}




}