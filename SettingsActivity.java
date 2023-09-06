package com.example.projcopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SettingsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button editprofile;
    TextView alwaysname;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    FirebaseStorage firebasestorage;
    StorageReference storageReference;
    TextView alwaysview;
    ImageView alwaysimage,logs,logsout;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.nav_settings);
        badgeDrawable.setVisible(true);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set settings selected
        bottomNavigationView.setSelectedItemId(R.id.nav_settings);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_settings:
                        return true;
                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_adddonations:
                        startActivity(new Intent(getApplicationContext(),AddPostactivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_donations:
                        startActivity(new Intent(getApplicationContext(),DonationsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        documentReference=db.collection("Users").document("Profile");
//        storageReference=firebasestorage.getReference("Profile Image");
        editprofile= findViewById(R.id.btneditprofile);
        alwaysimage=findViewById(R.id.prof);
        alwaysview=findViewById(R.id.alwaysname);
        logs=findViewById(R.id.log);
        alwaysname=findViewById(R.id.alwaysname);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    String Url_result=task.getResult().getString("Url");
                    Picasso.get().load(Url_result).into(alwaysimage);
                    String name_result=task.getResult().getString("Name");
                    alwaysname.setText(name_result);
                }
            }
        });


        logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showuserdetails(view);
            }
        });


    }
    public void showuserdetails(View view){
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    Intent intent=new Intent(SettingsActivity.this,showuserdetails.class);
                    startActivity(intent);

                }else{
                    Intent intent=new Intent(SettingsActivity.this,UserprofileActivity.class);
                    startActivity(intent);
                }

            }
        });
        Intent intent=new Intent(SettingsActivity.this,UserprofileActivity.class);
        startActivity(intent);




    }
}