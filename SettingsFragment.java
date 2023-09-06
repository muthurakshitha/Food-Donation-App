package com.example.projcopy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class SettingsFragment extends Fragment {
    Button editprofile;
    TextView alwaysname;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    FirebaseStorage firebasestorage;
    StorageReference storageReference;
    TextView alwaysview;
    ImageView alwaysimage,logs,logsout;



    public SettingsFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings, container, false);
        documentReference=db.collection("Users").document("Profile");
//        storageReference=firebasestorage.getReference("Profile Image");
        editprofile= view.findViewById(R.id.btneditprofile);
        alwaysimage=view.findViewById(R.id.alwaysprofile);
        alwaysview=view.findViewById(R.id.alwaysname);
        logs=view.findViewById(R.id.log);
        alwaysname=view.findViewById(R.id.alwaysname);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   showuserdetails(view);
            }
        });
        return view;

    }
    public void showuserdetails(View view){
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    Intent intent=new Intent(getContext(),showuserdetails.class);
                    startActivity(intent);

                }else{
                    Intent intent=new Intent(getContext(),UserprofileActivity.class);
                    startActivity(intent);
                }

            }
        });
            Intent intent=new Intent(getContext(),UserprofileActivity.class);
            startActivity(intent);

    }

}