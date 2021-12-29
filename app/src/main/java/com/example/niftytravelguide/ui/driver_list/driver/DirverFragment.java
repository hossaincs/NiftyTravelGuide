package com.example.niftytravelguide.ui.driver_list.driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.niftytravelguide.R;
import com.example.niftytravelguide.databinding.FragmentDirverBinding;
import com.example.niftytravelguide.ui.driver_list.chat.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DirverFragment extends Fragment {

    private FragmentDirverBinding binding;

    RecyclerView recyclerView;
    DriverAdapter adapterUsers;
    List<DriverModel> userList;
    FirebaseAuth firebaseAuth;
    FirebaseAuth mUser;
    private DriverAdapter.RecyclerViewClickClickListener listener;

    public DirverFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDirverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView =root.findViewById(R.id.driverRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseAuth =FirebaseAuth.getInstance();
        recyclerView.setHasFixedSize(true);
        mUser = FirebaseAuth.getInstance();

        userList=new ArrayList<DriverModel>();
//        setOnClickListener();
        getAllUsers();
        setOnClickListener();



        return root;
    }

    private void setOnClickListener() {
        listener = new DriverAdapter.RecyclerViewClickClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getContext(), ChatActivity.class);
                intent.putExtra("hisUid",userList.get(position).getUid());
                startActivity(intent);


            }

//            @Override
//            public void callOnClick(View v, int position) {
//                Toast.makeText(requireContext(),"call",Toast.LENGTH_SHORT).show();
//
//
//            }
        };
    }


    private void getAllUsers() {
        final FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Drivers");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    DriverModel modelUser=ds.getValue(DriverModel.class);
                    userList.add(modelUser);

                    adapterUsers=new DriverAdapter(getActivity(),userList,listener);
                    recyclerView.setAdapter(adapterUsers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchUsers(final String query) {

        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Drivers");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    DriverModel modelUser = ds.getValue(DriverModel.class);

                    if (modelUser.getUid()!= null &&!modelUser.getUid().equals(fuser.getUid())) {
                        if (modelUser.getName().toLowerCase() != null
                                && modelUser.getName().toLowerCase().contains(query.toLowerCase()))
                                {
                            userList.add(modelUser);
                        }
                    }


                    adapterUsers = new DriverAdapter(getActivity(), userList,listener);
                    adapterUsers.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterUsers);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                if(!TextUtils.isEmpty(q.trim())){
                    searchUsers(q);
                }else{
                    getAllUsers();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String q) {
                if(!TextUtils.isEmpty(q.trim())){
                    searchUsers(q);
                }else{
                    getAllUsers();
                }
                return false;
            }
        });




        super.onCreateOptionsMenu(menu,menuInflater);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}