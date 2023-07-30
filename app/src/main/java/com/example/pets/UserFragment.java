package com.example.pets;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserFragment extends Fragment {

    ImageButton editImg,log;
    ImageView userImg;


    TextView userEmail,userName;
    EditText contact;
    private static final int PICK_IMAGE = 100;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;

    Uri imageUri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        User user=new User();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("users/"+userId);

        View v= inflater.inflate(R.layout.fragment_user, container, false);

        editImg= v.findViewById(R.id.editImg);
        userImg= v.findViewById(R.id.userImg);
        userName= v.findViewById(R.id.userName);
        userEmail= v.findViewById(R.id.userEmail);
        contact= v.findViewById(R.id.userPhone);


        log= v.findViewById(R.id.logout);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();



//      Name
        DatabaseReference userRef = databaseReference.child("users").child(userId);
        DatabaseReference nameRef = userRef.child("name");

        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText( dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Email
        DatabaseReference emailRef = userRef.child("email");
        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userEmail.setText( dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Contact
        DatabaseReference ContactRef = userRef.child("contact");
        ContactRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contact.setText( dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        contact.setFocusable(false);
//        contact.addTextChangedListener(new TextWatcher() {
//                                             @Override
//                                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                                             }
//
//                                             @Override
//                                             public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                                                 Task<Void> Contact = userRef.child("name").setValue(contact.getText());
//                                                 Toast.makeText(getContext(), "Temperature " + contact.getText() + "update Sucessfully", Toast.LENGTH_SHORT);
//
//                                             }
//
//                                             @Override
//                                             public void afterTextChanged(Editable s) {
//
//                                             }
//                                         }
//        );


        editImg.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                     startActivityForResult(gallery, PICK_IMAGE);
//
                 }
             });


        return v;


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            userImg.setImageURI(imageUri);

        }
    }




}