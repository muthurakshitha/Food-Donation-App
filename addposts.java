package com.example.projcopy;

import static android.app.Activity.RESULT_OK;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import java.io.IOException;
import java.util.UUID;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class addposts extends Fragment {

    private EditText foodtime,prepare,availablee,zonee,addresss,landmark;
    private ImageView addphoto;
    private Button button;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String foodName, preparation,available,zone,address,locality,addimage,Time;
    ProgressBar progressBar;
    FirebaseStorage storage;
    StorageReference storageReference;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    public addposts(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_addpostsfragment, container, false);

        foodtime = view.findViewById(R.id.editText1);
        prepare = view.findViewById(R.id.editText2);
        availablee = view.findViewById(R.id.editText3);
        zonee = view.findViewById(R.id.editText4);
        addresss = view.findViewById(R.id.editText5);
        landmark = view.findViewById(R.id.editText6);
        addphoto = view.findViewById(R.id.imageView);
        button = view.findViewById(R.id.addpost);
        db = FirebaseFirestore.getInstance();
        progressBar=view.findViewById(R.id.progressBar);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });





        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // getting data from edittext fields.
                foodName = foodtime.getText().toString();
                preparation = prepare.getText().toString();
                available = availablee.getText().toString();
                zone=zonee.getText().toString();
                address=addresss.getText().toString();
                locality=landmark.getText().toString();
                addimage = filePath.toString();
                Time=FieldValue.serverTimestamp().toString();


                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(foodName)) {
                    foodtime.setError("Please enter Food Name");
                } else if (TextUtils.isEmpty(preparation)) {
                    prepare.setError("Please enter Preparation Time");
                } else if (TextUtils.isEmpty(available)) {
                    availablee.setError("Please enter Duration");
                } else if (TextUtils.isEmpty(zone)) {
                    zonee.setError("Please enter Zone");
                }else if (TextUtils.isEmpty(address)) {
                    addresss.setError("Please enter Address");
                }  else if (TextUtils.isEmpty(locality)) {
                    landmark.setError("Please enter Locality");
                }else if(filePath == null){
                    Toast.makeText(getContext(),"Please Upload a image!!",Toast.LENGTH_SHORT).show();
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(foodName, preparation, available,zone,address,locality,addimage,Time);
                    uploadImage();
                }

            }
        });
        return view;
    }

    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images" + UUID.randomUUID().toString()+".jpg");

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    progressBar.setVisibility(View.INVISIBLE);


                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);


                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }


    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(getContext().getContentResolver(), filePath);
                addphoto.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }



    private void addDataToFirestore(String foodName, String preparation, String available,String zone,String address,String locality,String addimage,String Time) {

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbPosts = db.collection("Donations");


        // adding our data to our courses object class.
        posts post = new posts(foodName, preparation, available,zone,address,locality,addimage,Time);

        // below method is use to add data to Firebase Firestore.
        dbPosts.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(getContext(), "Your Donation has been added ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(getContext(), "Fail to Post Your Donation!! Please Try Again \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
//        Fragment fragment = new HomeFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragmentContainerView5, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        Map<String, Object> user = new HashMap<>();
//        user.put("Foodtime", foodtime);
//        user.put("Prepare", prepare);
//        user.put("Available", available);
//        db.collection("users").add(user);
//        String foodTime=foodtime.getText().toString();
//        root.setValue(foodTime);
//        String Prepare=prepare.getText().toString();
//        root.setValue(Prepare);
//        String Available=available.getText().toString();
//        root.setValue(Available);
//        String Zone=zone.getText().toString();
//        root.setValue(Zone);
//        String Address=address.getText().toString();
//        root.setValue(Address);
//        String Landmark=landmark.getText().toString();
//        root.setValue(Landmark);
//        String addphoto=foodtime.getText().toString();
//        root.setValue(foodTime);

//      }
//    });
//
//   }
//}