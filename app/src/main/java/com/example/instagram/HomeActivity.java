package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.instagram.fragments.ComposeFragment;
import com.example.instagram.fragments.TimelineFragment;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    private Button signoutBtn;
    private Button postBtn;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        signoutBtn = findViewById(R.id.btnSignOut);
        postBtn = findViewById(R.id.btnPost);
//        rvPosts = findViewById(R.id.rvPosts);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // sign the user out and return to login screen
        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                final Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /* FRAGMENT handling */
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_capture:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_timeline:
                    default:
                        fragment = new TimelineFragment();
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // default selection
        bottomNavigationView.setSelectedItemId(R.id.action_timeline);


    }

}
