package com.example.niftytravelguide.ui.driver_list.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.niftytravelguide.databinding.FragmentWeatherBinding;
import com.example.niftytravelguide.ui.driver_list.weather.WeatherModels.OpenWeatherMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;

//    TextView txtTempWeather, txtWeatherConditionWeather, txtCityWeather, txtHumidityWeather, txtMinTempWeather, txtMaxTempWeather, txtPressureWeather, txtWindWeather;
//    ImageView imgWeather;
//    EditText edtTxtCityName;
//    Button btnSearch;
    LocationManager locationManager;
    LocationListener locationListener;
    double lat, lon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                lat = location.getLatitude();
                lon = location.getLongitude();

                Log.e("lat", String.valueOf(lat));
                Log.e("lan", String.valueOf(lon));

                getWeatherData(lat, lon);
            }
        };

        getPermissions();



        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
//                startActivity(intent);

            }
        });
        return root;
    }


    private void getPermissions() {

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,50,locationListener);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && permissions.length > 0 && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,50,locationListener);
        }
    }



    public void getWeatherData(double lat, double lon){
        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeatherMap> call = weatherAPI.getWeatherWithLocation(lat,lon);
        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {

                binding.txtCity.setText(response.body().getName()+" , " + response.body().getSys().getCountry());
                binding.txtTemp.setText(response.body().getMain().getTemp()+" °C");
                binding.txtWeatherCondition.setText(response.body().getWeather().get(0).getDescription());
                binding.txtHumidity.setText(" : "+response.body().getMain().getHumidity()+"%");
                binding.txtMaxTemp.setText(" : "+response.body().getMain().getTempMax()+" °C");
                binding.txtMinTemp.setText(" : "+response.body().getMain().getTempMin()+" °C");
                binding.txtPressure.setText(" : "+response.body().getMain().getPressure());
                binding.txtWind.setText(" : "+response.body().getWind().getSpeed());

                String iconCode = response.body().getWeather().get(0).getIcon();
                Glide.with(requireContext()).asBitmap().load("https://openweathermap.org/img/wn/10d@2x.png").into(binding.img);

            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable t) {

            }
        });
    }
}