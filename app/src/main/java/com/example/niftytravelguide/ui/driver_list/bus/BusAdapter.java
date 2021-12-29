package com.example.niftytravelguide.ui.driver_list.bus;

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


import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.MyHolder>  {
    Context context;
    List<BusModel> userList;

    public BusAdapter(Context context, List<BusModel> userList) {
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
        //final String hisUID=userList.get(position).getUid();
        String name=userList.get(position).getBusName();
        String des=userList.get(position).getBusDescription();
        String location=userList.get(position).getBusLocation();
        String price=userList.get(position).getBusprice();
        String counter=userList.get(position).getBusCounter();
        String mobile=userList.get(position).getBusNumber();
        String userimage=userList.get(position).getBusPostImage();

        holder.hotelName.setText(name);
        holder.hotelDes.setText(des);
        holder.hotelLoc.setText(location);
        holder.busprice.setText(price);
        holder.busCounter.setText(counter);
        holder.busMobile.setText(mobile);

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
        TextView hotelName,hotelDes,hotelLoc,busprice,busCounter,busMobile;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            hotelName=itemView.findViewById(R.id.hotelNameTextView);
            hotelDes=itemView.findViewById(R.id.descriptionTextView);
            hotelLoc=itemView.findViewById(R.id.locationTextView);
            mAvaterImage=itemView.findViewById(R.id.hotelImageView);
            busprice=itemView.findViewById(R.id.priceTextView);
            busCounter=itemView.findViewById(R.id.MobileNumberTextView);
            busMobile=itemView.findViewById(R.id.CounterNumberTextView);
        }
    }
}