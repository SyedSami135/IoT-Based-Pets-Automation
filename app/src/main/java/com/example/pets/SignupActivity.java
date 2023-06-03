package com.example.pets;

import static android.service.controls.ControlsProviderService.TAG;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {
TextView logIn;
ImageButton back;
Button signUp,signUpGoogle;

private FirebaseAuth mAuth;
private GoogleSignInOptions googleSignInOptions;
private GoogleSignInClient googleSignInClient;

private Uri Image;

private String name;
private String email;
private String password;



EditText etusername,etpass,etemail;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        etusername=findViewById(R.id.name);
        etemail=findViewById(R.id.email);
        etpass=findViewById(R.id.password);



        mAuth=FirebaseAuth.getInstance();




        googleSignInOptions=new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString((R.string.default_web_client_id)))
                .requestEmail()
                .build();
        googleSignInClient=GoogleSignIn.getClient(this,googleSignInOptions);



        logIn= findViewById(R.id.loginNow);
        logIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();


                }
        });

        back= findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUp= findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    createUSer();
                }
            }
        });

        signUpGoogle= findViewById(R.id.signUpWithGoogle);
        signUpGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
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

        password = etpass.getText().toString();
        if (TextUtils.isEmpty(password) && password.length()<7) {
            etpass.setError("Required.");
            valid = false;
        }name=etusername.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etusername.setError("Required.");
            valid = false;
        }else {
            valid=true;}

        return valid;
    }
    private void createUSer() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Authentication Successful",
                                    Toast.LENGTH_SHORT).show();
                            UpdateUser(name,email,"",null);
                            sendVerificationEmail();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed."+task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void UpdateUser(String name, String email ,String contact ,String imageUri){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

//         Send data to Firebase Realtime Database
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();



        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);
        userData.put("contact", contact);
        userData.put("photo", imageUri);

        databaseReference.child("users").child(userId).updateChildren(userData);





//        User user=new User(name,email,"","");
//        FirebaseDatabase.getInstance().getReference("users").
//                child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
//                    }
//                });
    }





//   ******************* Send Verification E-mail***************************
    private void sendVerificationEmail()
    {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);



                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }


//    *************************   WITH GOOGLE  ***************

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
                Log.d(ContentValues.TAG,"Firebase AuthWith Google:"+account.getId());
                Image =account.getPhotoUrl();
                task.getResult(ApiException.class);
                firebaseAuthwithGoogle(account.getIdToken());

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
                            UpdateUser(user.getDisplayName(), user.getEmail(), "","");
                            finish();
                        }
                    }
                });
    }




   }