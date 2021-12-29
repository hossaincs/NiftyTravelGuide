package com.example.niftytravelguide.ui.view_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import com.example.niftytravelguide.R;
import com.example.niftytravelguide.databinding.FragmentEditBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class EditFragment extends Fragment {
    private FragmentEditBinding binding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    String location,status,image;

    Uri imageUri;
    String myUri;
    StorageTask uploadTask;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    String change="No";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        databaseReference.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        String phone = String.valueOf(dataSnapshot.child("phone").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        image = String.valueOf(dataSnapshot.child("image").getValue());
                        location = String.valueOf(dataSnapshot.child("location").getValue());
                        status = String.valueOf(dataSnapshot.child("status").getValue());
                        binding.nameTextView.setText(phone);
                        binding.emailTextView.setText(email);
                        binding.textName.setText(name);
                        Glide.with(getActivity())
                                .load(image)
                                .placeholder(R.drawable.sample_img)
                                .into(binding.profileImage);





                    }else {


                    }


                }else {


                }

            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectImage();
                change="true";
            }
        });

        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(change=="true"){
                    uploadImage();
                    Navigation.findNavController(v).navigate(R.id.action_editFragment_to_navigation_notifications);

                }else{
                    uploadData();
                    Navigation.findNavController(v).navigate(R.id.action_editFragment_to_navigation_notifications);

                }

            }
        });





        return root;
    }
    private void uploadData() {


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");


        FirebaseUser user = mAuth.getCurrentUser();
        String uid=user.getUid();
                    HashMap<String, Object> hashMap= new HashMap<>();
        hashMap.put("uid",uid);
        if(location.equals("") ){
            hashMap.put("location","");
        }else{
            hashMap.put("location",location);

        }
        if(status.equals("") ){
            hashMap.put("status","");

        }else{
            hashMap.put("status",status);

        }
        if(image.equals("") ){
            hashMap.put("image","");

        }else{
            hashMap.put("image",image);
        }

                    hashMap.put("image", myUri);
                    hashMap.put("name", binding.textName.getText().toString());
                    hashMap.put("phone", binding.nameTextView.getText().toString());
                    hashMap.put("email",  binding.emailTextView.getText().toString());


                    reference.child(mAuth.getUid()).setValue(hashMap);




    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);






        uploadTask=storageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {

                    binding.profileImage.setImageURI(null);
                    Toast.makeText(requireContext(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(requireContext(),"Failed to Upload"+e,Toast.LENGTH_SHORT).show();


                    }
                });

        uploadTask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if(!task.isComplete()){
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloudUri= task.getResult();
                    myUri=downloudUri.toString();

                    Log.d("mainActivity",myUri.toString());

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");


                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid=user.getUid();
                    HashMap<String, Object> hashMap= new HashMap<>();

                    hashMap.put("uid",uid);
                    if(!location.equals("") ){
                        hashMap.put("location",location);
                    }else{
                        hashMap.put("location","");
                    }
                    if(!status.equals("") ){
                        hashMap.put("status",status);
                    }else{
                        hashMap.put("status","");
                    }
                    if(!status.equals("") ){
                        hashMap.put("status",status);
                    }else{
                        hashMap.put("status","");
                    }

                    hashMap.put("image", myUri);
                    hashMap.put("name", binding.textName.getText().toString());
                    hashMap.put("phone", binding.nameTextView.getText().toString());
                    hashMap.put("email",  binding.emailTextView.getText().toString());




                    reference.child(mAuth.getUid()).setValue(hashMap);



                }else{
                    Toast.makeText(requireContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            binding.profileImage.setImageURI(imageUri);


        }
    }
}