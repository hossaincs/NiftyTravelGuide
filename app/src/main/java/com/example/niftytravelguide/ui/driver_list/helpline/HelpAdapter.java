package com.example.niftytravelguide.ui.driver_list.helpline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.niftytravelguide.R;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyHolder>  {
    Context context;
    List<HelpModel> userList;

    public HelpAdapter(Context context, List<HelpModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_helpline,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String number=userList.get(position).getHelpNumber().toString();
        String org=userList.get(position).getHelpOrganization().toString();


        holder.helpLineOrg.setText(org);
        holder.helpLineNumber.setText(number);
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

        TextView helpLineNumber,helpLineOrg;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            helpLineNumber=itemView.findViewById(R.id.phoneTextView);
            helpLineOrg=itemView.findViewById(R.id.organizationTextView);

        }
    }
}