package com.example.niftytravelguide.ui.driver_list.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.niftytravelguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder> {
    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_RIGHT=1;
    Context context;
    List<ModelChat> chatList;
    String imageUrl;
    FirebaseUser fuser;

    public AdapterChat(Context context, List<ModelChat> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View view= LayoutInflater.from(context).inflate(R.layout.row_chat_right,parent,false);
            return new MyHolder(view);
        }else{
            View view= LayoutInflater.from(context).inflate(R.layout.row_chat_left,parent,false);
            return new MyHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") final int position) {
        String message=chatList.get(position).getMessage();
        String timeStamp=chatList.get(position).getTimestamp();
        //convert time

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
//        String dateTime = simpleDateFormat.format(new Date(Long.parseLong(timeStamp)));
        holder.messageTV.setText(message);
//        holder.timeTV.setText(dateTime);




        if(position==chatList.size()-1){
            if(chatList.get(position).isSeen()) {
                holder.isSeenTV.setText("Seen");
            }else{
                holder.isSeenTV.setText("Deliverd");
            }

        }else{
            holder.isSeenTV.setVisibility(View.GONE);
        }

    }

    private void deleteMessage(int position) {
        final String myUID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        String msgTimeStamp=chatList.get(position).getTimestamp();
        DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference("Chats");
        Query query=dbRef.orderByChild("timestamp").equalTo(msgTimeStamp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("sender").getValue().equals(myUID)){

                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("message","This message was deleted..");
                        ds.getRef().updateChildren(hashMap);
                        Toast.makeText(context,"message delete",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context,"you can delete only your meeage",Toast.LENGTH_SHORT).show();


                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }


    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        TextView messageTV,timeTV,isSeenTV;
        RelativeLayout messageLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            messageTV=itemView.findViewById(R.id.messageTv1);
            timeTV=itemView.findViewById(R.id.timeTV);
            isSeenTV=itemView.findViewById(R.id.seenTV);
            messageLayout=itemView.findViewById(R.id.messageLayout);

        }
    }
}
