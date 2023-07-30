package com.example.pets;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoginActivity extends AppCompatActivity {
    Button forget,logIn,logInGoogle;
    TextView signUp;
    EditText etpass,etemail;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;


    private String email,phone;
    Uri photoUri;
    private String pass;



    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         etemail=findViewById(R.id.email);
        etpass=findViewById(R.id.password);


        mAuth=FirebaseAuth.getInstance();

        googleSignInOptions=new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString((R.string.default_web_client_id)))
                .requestEmail()
                .build();
        googleSignInClient=GoogleSignIn.getClient(this,googleSignInOptions);

        signUp= findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });


        forget= findViewById(R.id.forgetPassword);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });


        logInGoogle= findViewById(R.id.logInGoogle);
        logInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
//
            }
        });


        logIn= findViewById(R.id.btnLogIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etemail.getText().toString())){
                    etemail.setError("Enter email");
                    etemail.requestFocus();
                }else if(TextUtils.isEmpty(etpass.getText().toString())){
                    etpass.setError("Enter password");
                    etpass.requestFocus();
                }else{
                   logInUser();
                }
            }
        });
    }

    private void logInUser() {

      if(  validateForm()){
          signInUser();
      }

    }

    @Override
    public void onStart() {
        super.onStart();
            if( FirebaseAuth.getInstance().getCurrentUser()!= null){
                if(mAuth.getCurrentUser().isEmailVerified()){

                    UpdateUser();
                   Home();
                }
            }
    }

    private void signInUser(){
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(getApplicationContext(), "Email Verified",
                                        Toast.LENGTH_SHORT).show();
                                UpdateUser();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Verify Email First.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private boolean validateForm() {
        boolean valid = true;

        email = etemail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etemail.setError("Required.");
            valid = false;
        }
        pass = etpass.getText().toString();
        if (TextUtils.isEmpty(pass) ) {
            etpass.setError("Required.");
            valid = false;
        }else {
            valid=true;}

        return valid;
    }


    private void signInWithGoogle() {

        Intent intent =googleSignInClient.getSignInIntent();
        startActivityForResult(intent,65);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==65){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                Log.d(TAG,"Firebase AuthWith Google:"+account.getId());
                task.getResult(ApiException.class);
                firebaseAuthwithGoogle(account.getIdToken());
                photoUri= account.getPhotoUrl();
                }catch (ApiException e){}

        }

    }


    private void firebaseAuthwithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(ContentValues.TAG,"Sign In with Google Successfully");
                            FirebaseUser user=mAuth.getCurrentUser();
                            User users=new User(user.getDisplayName(), user.getEmail(), "",photoUri);
                            finish();
                        }
                    }
                });
    }

    public void Home(){

        startActivity(new Intent(getApplicationContext(),Home.class));
    }


    protected void UpdateUser(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                Home();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Bad",
                        Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
            }
        });


    }

}