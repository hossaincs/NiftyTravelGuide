package com.example.niftytravelguide.ui.driver_list.hotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.niftytravelguide.R;
import com.example.niftytravelguide.ui.driver_list.hotel.model.HotelModel;


import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyHolder>  {
    Context context;
    List<HotelModel> userList;

    public HotelAdapter(Context context, List<HotelModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_air,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String name=userList.get(position).getHotelname();
        String des=userList.get(position).getHoteldescription();
        String location=userList.get(position).getHotellocation();

        String userimage=userList.get(position).getHotelpostimage();
        String price=userList.get(position).getHotelprice();

        String mobile=userList.get(position).getHotelNumber();

        holder.busprice.setText(price);
        holder.busMobile.setText(mobile);
        holder.hotelName.setText(name);
        holder.hotelDes.setText(des);
        holder.hotelLoc.setText(location);

        Glide.with(context)
                .load(userimage)
                .placeholder(R.drawable.phot)
                .into(holder.mAvaterImage);


    }

    @Override
    public int getItemCount() {
        if (userList==null || userList.size()==0){
            return 0;
        }else{
            return userList.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView mAvaterImage;
        TextView hotelName,hotelDes,hotelLoc,busprice,busMobile;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            hotelName=itemView.findViewById(R.id.hotelNameTextView);
            hotelDes=itemView.findViewById(R.id.descriptionTextView);
            hotelLoc=itemView.findViewById(R.id.locationTextView);
            mAvaterImage=itemView.findViewById(R.id.hotelImageView);

            busprice=itemView.findViewById(R.id.priceTextView);
            busMobile=itemView.findViewById(R.id.CounterNumberTextView);

        }
    }
}