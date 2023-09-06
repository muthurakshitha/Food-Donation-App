package com.example.projcopy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class MainActivity2 extends AppCompatActivity {

    public static final String TAG = "Tag";
    TextView alreadyhaveanaccount;
    EditText email,password,confirmpassword,phone,Username;
    Button register;
    String emailpattern="^[A-Za-z0-9+_.-]+@(.+)$",userid;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        alreadyhaveanaccount=findViewById(R.id.alreadyhaveanaccount);
        Username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        phone=findViewById(R.id.phone);
        register=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        fstore=FirebaseFirestore.getInstance();




        alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,MainActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });}

    private void PerforAuth() {
        String user = Username.getText().toString();
        String emails = email.getText().toString();
        String password1 = password.getText().toString().trim();
        String password2 = confirmpassword.getText().toString().trim();
        String phno = phone.getText().toString();

        if (user.isEmpty()) {
            Username.setError("Enter Username");
            Username.requestFocus();
            } else if (!emails.matches(emailpattern)) {
                email.setError("Enter Correct Email");
                email.requestFocus();
            } else if (password1.isEmpty() || password1.length() < 6) {
                password.setError("Enter proper password with atleast more than 6 characters");
                password.requestFocus();
            } else if (!password1.equals(password2)) {
                confirmpassword.setError("Passwords not matching!!");
                confirmpassword.requestFocus();
            }else if (phno.isEmpty()) {
                phone.setError("Phone no required");
                phone.requestFocus();
        }else {
                progressDialog.setMessage("Registration..");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(emails, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            addDataToFirestore(user, emails, phno);
                            sendUserToNextActivity();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity2.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }


    private void sendUserToNextActivity() {
        Intent intent=new Intent(MainActivity2.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void addDataToFirestore(String username, String emails, String phno) {

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbPosts = fstore.collection("Registered Users");

        // adding our data to our courses object class.
        registrationdetails pro = new registrationdetails(username,emails,phno);

        // below method is use to add data to Firebase Firestore.
        dbPosts.add(pro).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(MainActivity2.this, "You Registered Successfully ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(MainActivity2.this, "Fail To Register!! Please Try Again \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
   }


}