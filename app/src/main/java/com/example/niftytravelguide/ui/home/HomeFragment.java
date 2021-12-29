package com.example.niftytravelguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.niftytravelguide.R;
import com.example.niftytravelguide.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.cardViewBus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_hotelFragment);

            }
        });

        binding.cardViewAir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_touristPlaceFragment);

            }
        });

        binding.cardViewHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_helpFragment);

            }
        });

        binding.cardView7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_busDetailsFragment);

            }
        });
        binding.cardViewTourist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_airFragment);

            }
        });

        binding.cardViewgps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_mapsFragment);
               // Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_mapsFragment2);

            }
        });

        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_weatherFragment);

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