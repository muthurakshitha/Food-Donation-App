package com.example.projcopy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class showuserdetails extends AppCompatActivity {
    FirebaseStorage firebasestorage;
    StorageReference storageReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ImageView imageview;
    TextView name,email,gender,dob,phno;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showuserdetails);


        name=findViewById(R.id.fullname);
        email=findViewById(R.id.email_text);
        gender=findViewById(R.id.gender_text);
        dob=findViewById(R.id.dob_text);
        phno=findViewById(R.id.phone_text);
        floatingActionButton=findViewById(R.id.floatingbtn);
        imageview=findViewById(R.id.user_profile);
        documentReference=db.collection("Users").document("Profile");
        storageReference=firebasestorage.getInstance().getReference("Profile Image");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(showuserdetails.this,UserprofileActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onStart(){
        super.onStart();
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            String name_result=task.getResult().getString("Name");
                            String email_result=task.getResult().getString("Email");
                            String gender_result=task.getResult().getString("Gender");
                            String dob_result=task.getResult().getString("DOB");
                            String phno_result=task.getResult().getString("PhoneNo");
                            String Url_result=task.getResult().getString("Url");

                            Picasso.get().load(Url_result).into(imageview);
                            name.setText(name_result);
                            email.setText(email_result);
                            gender.setText(gender_result);
                            dob.setText(dob_result);
                            phno.setText(phno_result);
                        }else{
                            Toast.makeText(showuserdetails.this,"No Profile Exist",Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}