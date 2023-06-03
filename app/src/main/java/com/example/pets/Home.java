package com.example.pets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {


    Button log;
    ToggleButton lightbtn;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;
    private FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    CameraFragment cameraFragment;
    NotificationFragment notificationFragment;
    ScheduleFragment scheduleFragment;
    UserFragment userFragment;
    User users;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        homeFragment=new HomeFragment();
        cameraFragment=new CameraFragment();
        userFragment=new UserFragment();
        notificationFragment=new NotificationFragment();
        scheduleFragment=new ScheduleFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeFragment).commit();


        mAuth=FirebaseAuth.getInstance();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

//        log= findViewById(R.id.logout);
//        log.setVisibility(View.INVISIBLE);
//        log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//            }
//        });




        bottomNavigationView= findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        changeFragment(homeFragment);
                        return true;
                    case R.id.camera:

                        changeFragment(cameraFragment);
                        Toast.makeText(Home.this, "Camera", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.schedule:
                        changeFragment(scheduleFragment);
                        return true;
                    case R.id.notification:
                        changeFragment(notificationFragment);
                        return true;

                    case R.id.userInfo:
                        changeFragment(userFragment);
                        return true;

                }
                return false;
            }
        });


    }
 private void changeFragment(Fragment fragment){
     FragmentManager fragmentManager=getSupportFragmentManager();
     FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
     fragmentTransaction.replace(R.id.frame,fragment);
     fragmentTransaction.commit();

 }

    @Override
    protected void onStart() {
        super.onStart();
      if(  FirebaseAuth.getInstance().getCurrentUser()==null){
          Intent intent=new Intent(Home.this,LoginActivity.class);
          startActivity(intent);
      }
    }
}



