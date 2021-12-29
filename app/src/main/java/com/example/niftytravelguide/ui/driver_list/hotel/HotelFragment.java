package com.example.niftytravelguide.ui.driver_list.hotel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.niftytravelguide.R;
import com.example.niftytravelguide.databinding.FragmentHotelBinding;
import com.example.niftytravelguide.ui.driver_list.hotel.adapter.HotelAdapter;
import com.example.niftytravelguide.ui.driver_list.hotel.model.HotelModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HotelFragment extends Fragment {

    private FragmentHotelBinding binding;

    RecyclerView recyclerView;
    HotelAdapter adapterUsers;
    List<HotelModel> userList;
    FirebaseAuth firebaseAuth;
    FirebaseAuth mUser;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHotelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView=root.findViewById(R.id.hotelRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseAuth =FirebaseAuth.getInstance();
        recyclerView.setHasFixedSize(true);
        mUser = FirebaseAuth.getInstance();

        userList=new ArrayList<HotelModel>();
//        setOnClickListener();
        getAllUsers();



        return root;
    }

    private void getAllUsers() {
        final FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("HotelInformation");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //userList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    HotelModel modelUser=ds.getValue(HotelModel.class);
                    userList.add(modelUser);

                    adapterUsers=new HotelAdapter(getActivity(),userList);
                    recyclerView.setAdapter(adapterUsers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}