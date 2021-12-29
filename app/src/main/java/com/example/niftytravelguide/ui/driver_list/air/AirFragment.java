package com.example.niftytravelguide.ui.driver_list.air;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.niftytravelguide.R;
import com.example.niftytravelguide.databinding.FragmentAirBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AirFragment extends Fragment {
    FragmentAirBinding binding;

    RecyclerView recyclerView;
    AirAdapter adapterUsers;
    List<AirModel> userList;
    FirebaseAuth firebaseAuth;
    FirebaseAuth mUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView=root.findViewById(R.id.airRvv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseAuth =FirebaseAuth.getInstance();
        recyclerView.setHasFixedSize(true);
        mUser = FirebaseAuth.getInstance();

        userList=new ArrayList<AirModel>();
//        setOnClickListener();
        getAllUsers();
        return root;
    }

    private void getAllUsers() {
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AirInformation");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //userList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AirModel modelUser = ds.getValue(AirModel.class);
                    userList.add(modelUser);

                    adapterUsers = new AirAdapter(getActivity(), userList);
                    recyclerView.setAdapter(adapterUsers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}