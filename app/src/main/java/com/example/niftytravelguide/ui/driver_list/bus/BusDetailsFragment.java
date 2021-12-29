package com.example.niftytravelguide.ui.driver_list.bus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.niftytravelguide.R;
import com.example.niftytravelguide.databinding.FragmentBusDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BusDetailsFragment extends Fragment {
    private FragmentBusDetailsBinding binding;

    RecyclerView recyclerView;
    BusAdapter adapterUsers;
    List<BusModel> userList;
    FirebaseAuth firebaseAuth;
    FirebaseAuth mUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBusDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView =root.findViewById(R.id.busRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseAuth =FirebaseAuth.getInstance();
        recyclerView.setHasFixedSize(true);
        mUser = FirebaseAuth.getInstance();

        userList=new ArrayList<BusModel>();
//        setOnClickListener();
        getAllUsers();



        return root;
    }

    private void getAllUsers() {

        final FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("BusInformation");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //userList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    BusModel modelUser=ds.getValue(BusModel.class);
                    userList.add(modelUser);

                    adapterUsers=new BusAdapter(getActivity(),userList);
                    recyclerView.setAdapter(adapterUsers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}