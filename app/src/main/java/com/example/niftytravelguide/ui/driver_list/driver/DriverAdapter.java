package com.example.niftytravelguide.ui.driver_list.driver;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.niftytravelguide.R;


import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyHolder>  {
    Context context;
    List<DriverModel> userList;
    private RecyclerViewClickClickListener listener;

    public DriverAdapter(Context context, List<DriverModel> userList,RecyclerViewClickClickListener listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_driver,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //final String hisUID=userList.get(position).getUid();
        String name=userList.get(position).getName();
        String mobile=userList.get(position).getPhone();
        String location=userList.get(position).getLocation();;
        String userimage=userList.get(position).getImage();

        holder.dName.setText(name);
        holder.dLocation.setText(location);
        holder.dMobile.setText(mobile);

        holder.callB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "call",Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+mobile));
                if (ActivityCompat.checkSelfPermission(view.getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(callIntent);
            }
        });

        Glide.with(context)
                .load(userimage)
                .placeholder(R.drawable.sample_img)
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

    public interface RecyclerViewClickClickListener{
        void onClick(View v,int position);


    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mAvaterImage,callB,msgB;
        TextView dName,dMobile,dLocation;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            dName=itemView.findViewById(R.id.DNameTextView);
            dMobile=itemView.findViewById(R.id.DMobileTextView);
            dLocation=itemView.findViewById(R.id.DLocationTextView);
            msgB=itemView.findViewById(R.id.DRequestTextView);
            callB=itemView.findViewById(R.id.DCall);

            mAvaterImage=itemView.findViewById(R.id.DavaterImage);

            msgB.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(callB,getAdapterPosition());
            //listener.callOnClick(msgB,getAdapterPosition());

        }
    }
}
