package com.example.niftytravelguide.ui.registrar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import com.example.niftytravelguide.MainActivity;
import com.example.niftytravelguide.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();



        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });



    }

    private void createUser() {
        final String email=binding.editTextEmail.getText().toString().trim();
        final String password=binding.editTextpassword.getText().toString().trim();
        if(email.isEmpty()){
            binding.editTextEmail.setError("Email Required");
            binding.editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            binding.editTextpassword.setError("Password Required");
            binding.editTextpassword.requestFocus();
            return;
        }
        if(password.length()<6){
            binding.editTextpassword.setError("Password Should be atleast six character");
            binding.editTextpassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object,String> hashMap=new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name",binding.editTextName.getText().toString());
                            hashMap.put("phone",binding.editTextPhone.getText().toString());
                            hashMap.put("image","");
                            hashMap.put("cover","");
                            hashMap.put("status","");
                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference=database.getReference("Users");
                            reference.child(uid).setValue(hashMap);
                            startProfileActivity();

                            Toast.makeText(RegisterActivity.this,"Wellcome  "+user.getEmail(),Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.


                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();


                        }


                    }
                });
    }

    private void startProfileActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }
}