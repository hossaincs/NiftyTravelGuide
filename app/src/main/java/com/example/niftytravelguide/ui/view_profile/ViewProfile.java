package com.example.niftytravelguide.ui.view_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import com.example.niftytravelguide.R;
import com.example.niftytravelguide.databinding.FragmentProfileBinding;
import com.example.niftytravelguide.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewProfile extends Fragment {

    private FragmentProfileBinding binding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_notifications_to_editFragment);

            }
        });
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_notifications_to_editFragment);

            }
        });

        databaseReference.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        String phone = String.valueOf(dataSnapshot.child("phone").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String name = String.valueOf(dataSnapshot.child("name").getValue());

                        binding.nameTextView.setText(phone);
                        binding.emailTextView.setText(email);
                        binding.textName.setText(name);
                        String image = String.valueOf(dataSnapshot.child("image").getValue());
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



        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}