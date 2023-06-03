package com.example.pets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class OTPHandler extends AppCompatActivity {

    ImageButton back;
    Button conToLogin;
    EditText opt1,opt2,opt3,opt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_otphandler);

            Intent intent=getIntent();
            int otp= intent.getIntExtra("OTP",0000);
            String email=intent.getStringExtra("Email");


            opt1 = findViewById(R.id.opt1);
            opt1.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.showSoftInput(opt1, InputMethodManager.SHOW_IMPLICIT);

            back= findViewById(R.id.goBack);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            conToLogin= findViewById(R.id.conToLogin);
            conToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String string=opt1.getText().toString()+
                            opt2.getText().toString()+
                            opt3.getText().toString()+
                            opt4.getText().toString();
                    int code= Integer.parseInt(string);

                    if(otp==code){
                        Intent intent1=new Intent(getApplicationContext(),ResetPassword.class);
                        intent1.putExtra("Email",email);
                    }else {
                        opt1.setText("");
                        opt2.setText("");
                        opt3.setText("");
                        opt4.setText("");
                        Toast.makeText(OTPHandler.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                    }

                }
            });







    }
}