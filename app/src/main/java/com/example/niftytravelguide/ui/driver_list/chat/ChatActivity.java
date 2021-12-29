package com.example.niftytravelguide.ui.driver_list.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.niftytravelguide.R;
import com.example.niftytravelguide.ui.driver_list.driver.DriverModel;
import com.example.niftytravelguide.ui.driver_list.notification.APIService;
import com.example.niftytravelguide.ui.driver_list.notification.Client;
import com.example.niftytravelguide.ui.driver_list.notification.Data;
import com.example.niftytravelguide.ui.driver_list.notification.MyResponse;
import com.example.niftytravelguide.ui.driver_list.notification.Sender;
import com.example.niftytravelguide.ui.driver_list.notification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    LogInViewModel logInViewModel;

    RecyclerView recyclerView;

    CircleImageView profile_image;
    TextView username,online,location;

    FirebaseUser fuser;
    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;
    FirebaseAuth firebaseAuth;
    String userid;
    FirebaseAuth mUser;
    ValueEventListener seenListener;


    List<ModelChat> mchat;
    AdapterChat messageAdapter;

    APIService apiService;
    boolean notify = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        online = findViewById(R.id.online);
        location = findViewById(R.id.userlocation);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        mUser = FirebaseAuth.getInstance();
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        logInViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(LogInViewModel.class);


        Intent intent = getIntent();
        userid = intent.getStringExtra("hisUid");
        firebaseAuth = FirebaseAuth.getInstance();

        Log.d("token ", " " + userid);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String msg = text_send.getText().toString();
                String time = String.valueOf(System.currentTimeMillis());
                if (!msg.equals("")){
                    sendMessage(fuser.getUid(), userid, msg, time);
                } else {
                    Toast.makeText(ChatActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Drivers").child(userid);
        Log.d("uid",userid);
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String location1 = String.valueOf(dataSnapshot.child("location").getValue());
                        String online1 = String.valueOf(dataSnapshot.child("status").getValue());

                        if(location1.equals("")){
                            location.setText("");
                        }else{
                            location.setText(location1);
                        }
                        if(online1.equals("")){
                            online.setText("offline");
                        }else{
                            online.setText(online1);
                        }
                        username.setText(name);



                        String image = String.valueOf(dataSnapshot.child("image").getValue());
                        Glide.with(getApplicationContext())
                                .load(image)
                                .placeholder(R.drawable.sample_img)
                                .into(profile_image);


                    }else {


                    }


                }else {


                }

            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DriverModel user = dataSnapshot.getValue(DriverModel.class);



                readMesagges(fuser.getUid(), userid, user.getImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(userid);



    }

// reference = FirebaseDatabase.getInstance().getReference("Chats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mchat.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    ModelChat chat = snapshot.getValue(ModelChat.class);
//                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
//                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
//                        mchat.add(chat);
//                    }
//
//                    messageAdapter = new AdapterChat(ChatActivity.this, mchat, imageurl);
//                    recyclerView.setAdapter(messageAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
    private void seenMessage(final String userid){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ModelChat chat = dataSnapshot.getValue(ModelChat.class);
                    Log.d("getReceive ", " " + fuser.getUid());
                    Log.d("userid ", " " + userid);
                    if (chat.getReceiver() != null && chat.getSender() != null && chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        dataSnapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendMessage(String sender, final String receiver, String message, String time){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        hashMap.put("time", time);

        reference.child("Chats").push().setValue(hashMap);


        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.getUid())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(fuser.getUid());
        chatRefReceiver.child("id").setValue(fuser.getUid());

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DriverModel user = dataSnapshot.getValue(DriverModel.class);
                if (notify) {
                    sendNotifiaction(receiver, user.getName(), msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotifiaction(String receiver, final String username, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.drawable.sample_img, username+": "+message, "New Message",
                            userid);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            //Toast.makeText(MessageActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMesagges(final String myid, final String userid, final String imageurl){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ModelChat chat = snapshot.getValue(ModelChat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }

                    messageAdapter = new AdapterChat(ChatActivity.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void currentUser(String userid){
        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        editor.putString("currentuser", userid);
        editor.apply();
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
        currentUser(userid);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        status("offline");
        currentUser("none");
    }



//        firebaseDatabase=FirebaseDatabase.getInstance();
//        userDbref=firebaseDatabase.getReference("Drivers");
//        //search user to get their user info
//        Query userQuery=userDbref.orderByChild("uid").equalTo(hisUid);
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        myUid=user.getUid();
//
//        userQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //check until required is received
//                for(DataSnapshot ds:snapshot.getChildren()){
//                    String name=""+ ds.child("name").getValue();
//
//
//                    String typingStatus=""+ ds.child("typingTo").getValue();
//
//                    if(typingStatus.equals(myUid)){
//                        userStatusTv1.setText("typing..");
//                    }else{
//                        String onlineStatus=""+ ds.child("onlineStatus").getValue();
//                        if(onlineStatus.equals("online")){
//                            userStatusTv1.setText(onlineStatus);
//                        }else{
//
//                        }
//                    }
//                    nameTv.setText(name);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        token = task.getResult();
//
//                        //String msgt = getString(R.string.msg_token_fmt, token);
//                        HashMap hashMap = new HashMap();
//
//
//                        hashMap.put("token",token);
//
//                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
//
//                        ref.child(user.getUid()).updateChildren(hashMap);
//                        Log.d("token "," "+token);
//
//
//                    }
//                });
//
//
//
//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                notify = true;
//                String message=massageEt.getText().toString().trim();
//                if(TextUtils.isEmpty(message)){
//                    Toast.makeText(ChatActivity.this,"Cannot send the empty message",Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    sendMessage(message);
//                }
//
//                massageEt.setText("");
//
//            }
//        });
//        massageEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if(s.toString().trim().length()==0){
//                    checkTypingStatus("noOne");
//                }
//                else{
//                    checkTypingStatus(hisUid);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//
//        readMessages();
//        seenMessages();
//
//
//
//    }
//
//    private void seenMessages() {
//        userRefForSeen=FirebaseDatabase.getInstance().getReference("Chats");
//        seenlistener=userRefForSeen.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds:snapshot.getChildren()){
//                    ModelChat chat=ds.getValue(ModelChat.class);
//                    if(chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid)){
//                        HashMap<String,Object> hasseenHashMap=new HashMap<>();
//                        hasseenHashMap.put("isSeen",true);
//                        ds.getRef().updateChildren(hasseenHashMap);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void readMessages(){
//        chatList=new ArrayList<>();
//        DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference("Chats");
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                chatList.clear();
//                for(DataSnapshot ds:snapshot.getChildren()){
//                    ModelChat chat=ds.getValue(ModelChat.class);
//                    if(chat.getReceiver().equals(myUid)&&chat.getSender().equals(hisUid)||
//                            chat.getReceiver().equals(hisUid)&&chat.getSender().equals(myUid)){
//                        chatList.add(chat);
//                    }
//                    adapterChat=new AdapterChat(ChatActivity.this,chatList,hisImage);
//                    adapterChat.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapterChat);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void sendMessage(String message) {
//        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
//        String timestamp=String.valueOf(System.currentTimeMillis());
//        HashMap<String,Object> hashMap=new HashMap<>();
//        hashMap.put("sender",myUid);
//        hashMap.put("receiver",hisUid);
//        hashMap.put("message",message);
//        hashMap.put("timestamp",timestamp);
//        hashMap.put("isSeen",false);
//        databaseReference.child("Chats").push().setValue(hashMap);
//
//
//        String msg = message;
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                DriverModel user = snapshot.getValue(DriverModel.class);
//                assert user != null;
//
//                if(notify){
//                    sendNotification(hisUid,user.getName(),msg);
//                    Log.d("my uid "," "+myUid);
//                    Log.d("sent notification"," "+hisUid +" toke"+user.getName()+"his "+msg);
//                }
//                notify = false;
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//
//
//
//    private void sendNotification(String hisUid, String name, String msg) {
//        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
//
//        Query query = allTokens.orderByKey().equalTo(hisUid);
//
//        String tokent=allTokens.child(hisUid).child("token").toString();
//        Data data = new Data(myUid,name+":"+msg,"New Message",hisUid, String.valueOf(R.drawable.ic_baseline_textsms_24));
//        Log.d("notification"," "+myUid +" toke "+tokent+"his "+hisUid);
//
//
//        Sender sender = new Sender(data,tokent);
//        apiService.sendNotification(sender)
//                .enqueue(new Callback<MyResponse>() {
//
//                    @Override
//                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                        if (response.code() == 200) {
//                            Log.d("appp88pp"," app88ppiii");
//                            assert response.body() != null;
//                            if (response.body().success != 1) {
//                                Log.d("chat"," succ");
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MyResponse> call, Throwable t) {
//                        Log.d("notification"," eee");
//
//                    }
//                });
//
//    }
//
//    private void checkUserStatus(){
//        FirebaseUser user=firebaseAuth.getCurrentUser();
//        if(user!=null){
//            myUid=user.getUid();
//
//            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
//            editor.putString("currentuser", myUid);
//            editor.apply();
//
//        }else{
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }
//    }
//
//    private void checkOnlineStatus(String status){
//        DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference("Users").child(myUid);
//        HashMap<String,Object> hashMap=new HashMap<>();
//        hashMap.put("onlineStatus",status);//onlineStatus
//        dbRef.updateChildren(hashMap);
//    }
//    private void checkTypingStatus(String typing){
//        DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference("Users").child(myUid);
//        HashMap<String,Object> hashMap=new HashMap<>();
//        hashMap.put("typingTo",typing);//typingTo
//        dbRef.updateChildren(hashMap);
//    }
//
//
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        //get timestampp
//        String timestamp=String.valueOf(System.currentTimeMillis());
//        checkOnlineStatus(timestamp);
//        checkTypingStatus("noOne");
//
//        userRefForSeen.removeEventListener(seenlistener);
//    }
//
//    @Override
//    protected void onResume() {
//
//        checkOnlineStatus("online");
//
//        super.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        checkUserStatus();
//        checkOnlineStatus("online");
//        super.onStart();
//    }
//
//
//
//

}