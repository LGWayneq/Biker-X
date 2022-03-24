package com.example.bikerx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bikerx.control.ApiManager;
import com.example.bikerx.control.DBManager;
import com.example.bikerx.control.LocationManager;
import com.example.bikerx.ui.home.HomeViewModel;
import com.example.bikerx.ui.session.CyclingSessionFragment;
import com.example.bikerx.ui.home.Route1;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bikerx.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    public static final String ANONYMOUS = "anonymous";

    private GoogleSignInClient mSignInClient;
    private ActivityMainBinding mBinding;
    private FirebaseAuth mFirebaseAuth;
    private String userId;
    private NavController navController;
    private LocationManager locationManager = new LocationManager(this);
    public BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // Initialize Firebase Auth and check if the user is signed in
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        // random shit;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this, gso);

        setUpActionBar();
        setUpBottomNavigationBar();
        locationManager.getLocationPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signout) {
            signOut();
        }
        if (id == R.id.settings) {
            navController.navigate(R.id.settingsFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpActionBar() {
        getSupportActionBar().setTitle("Hello, "+ getUserName() + "!");
    }

    private void setUpBottomNavigationBar() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_history, R.id.navigation_chat)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.navView, navController);
        bottomNavigationView = mBinding.navView;
        FragmentManager fm = this.getSupportFragmentManager();
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { //hacky fix to navigation issue
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        navController.navigate(R.id.navigation_home);
                        return true;

                    case R.id.navigation_map:
                        navController.navigate(R.id.navigation_map);
                        return true;

                    case R.id.navigation_history:a:
                    navController.navigate(R.id.navigation_history);
                        return true;

                    case R.id.navigation_chat:
                        navController.navigate(R.id.navigation_chat);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    private void signOut() {
        final Intent loginIntent = new Intent(this, LoginActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_signout_title);
        builder.setMessage(R.string.confirm_signout_message);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mFirebaseAuth.signOut();
                mSignInClient.signOut();
                startActivity(loginIntent);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            userId = mFirebaseAuth.getUid();
            return user.getDisplayName();
        }

        return "Anonymous";
    }

    public String getUserId() {
        return this.userId;
    };

}