package com.example.projcopy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UserprofileActivity extends AppCompatActivity {
    EditText fullname,email,gender,dob,phone;
    Button button;
    ProgressBar progressbar;
    private Uri imageuri;
    String ur;
    private static  final int pick_image=1;
    UploadTask uploadtask;
    FirebaseStorage firebasestorage;
    StorageReference storageReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        imageview=findViewById(R.id.user_profile);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email_text);
        gender=findViewById(R.id.gender_text);
        dob=findViewById(R.id.dob_text);
        phone=findViewById(R.id.phone_text);
        button=findViewById(R.id.saveprofile);
        progressbar=findViewById(R.id.progress_bar);

        documentReference=db.collection("Users").document("Profile");
        storageReference=firebasestorage.getInstance().getReference("Profile Image");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadData();
            }
        });
    }
    public void ChooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,pick_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pick_image || resultCode == RESULT_OK || data != null || data.getData() != null) {
            imageuri = data.getData();
            Picasso.get().load(imageuri).into(imageview);
        }
    }
//    private Uri getFileExt(String ur) {
//        String type = null;
//        String extension = MimeTypeMap.getFileExtensionFromUrl(ur);
//        if (extension != null) {
//            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//        }
//        Uri typ=Uri.parse(type);
//        Toast.makeText(UserprofileActivity.this, "Success", Toast.LENGTH_SHORT).show();
//        return typ;
//    }
    private void UploadData() {
        String name=fullname.getText().toString();
        String emails=email.getText().toString();
        String gen=gender.getText().toString();
        String dobir=dob.getText().toString();
        String ph=phone.getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(emails) || TextUtils.isEmpty(gen) || TextUtils.isEmpty(dobir) || TextUtils.isEmpty(ph) || imageuri!=null){
            progressbar.setVisibility(View.VISIBLE);
            final StorageReference reference=storageReference.child(System.currentTimeMillis() + "."+"jpg");
            uploadtask=reference.putFile(imageuri);
            Task<Uri> urlTask= uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return reference.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()) {
                                Uri downloadUri=task.getResult();
                                Map<String, String> profile=new HashMap<>();
                                profile.put("Name",name);
                                profile.put("Email",emails);
                                profile.put("Gender",gen);
                                profile.put("DOB",dobir);
                                profile.put("PhoneNo",ph);
                                profile.put("Url",downloadUri.toString());

                                documentReference.set(profile)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressbar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(UserprofileActivity.this,"Profile has been created",Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(UserprofileActivity.this,showuserdetails.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(UserprofileActivity.this, "Profile Creation Failed Try Again!!", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }else{
            Toast.makeText(this,"All Fields required!!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
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
                            fullname.setText(name_result);
                            email.setText(email_result);
                            gender.setText(gender_result);
                            dob.setText(dob_result);
                            phone.setText(phno_result);
                        }else{
                            Toast.makeText(UserprofileActivity.this,"No Profile Exist",Toast.LENGTH_SHORT).show();
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