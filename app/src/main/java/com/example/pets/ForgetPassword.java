package com.example.pets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class ForgetPassword extends AppCompatActivity {
    Button toOTP;
    ImageButton back;
    EditText email;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth=FirebaseAuth.getInstance();

        toOTP = findViewById(R.id.continue_forget_password);
        toOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            email= findViewById(R.id.email);

//klsdf
            String Email=email.getText().toString();
            generateOTP(Email);

            }

        });
        back= findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void generateOTP(String email){
        @SuppressLint("DefaultLocale")
        String otp= String.format("%04d", new Random().nextInt(10000));
        FirebaseAuth.getInstance().sendSignInLinkToEmail(email,ActionCodeSettings.newBuilder()
                        .setUrl("TeamPets@auto-pets.firebaseapp.com")
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName("com.example.pets",true,null)
                        .build()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Log.d(TAG, "OTP sent to email: " + email);
                    Intent intent=new Intent(getApplicationContext(), OTPHandler.class);
                    intent.putExtra("OTP" ,otp);
                    intent.putExtra("Email",email);
                    startActivity(intent);
                }else{
                    Log.d(TAG, "Error sending OTP to email: " + task.getException().getMessage());
                }

            }
        });



    }



}
