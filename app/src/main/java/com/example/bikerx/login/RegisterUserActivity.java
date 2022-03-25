package com.example.bikerx.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.example.bikerx.R;
import com.example.bikerx.databinding.ActivityRegisterUserBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterUserBinding mBinding;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mBinding.registerUser.setOnClickListener(view -> verifyDetails());
        dialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                verifyDetails();
                break;
        }
    }

    private void verifyDetails() {
        //convert inputs in the register page to strings
        String email = mBinding.email.getText().toString().trim();
        String password = mBinding.password.getText().toString().trim();
        String fullName = mBinding.fullName.getText().toString().trim();

        //validate inputs
        if (fullName.isEmpty()) {
            mBinding.fullName.setError("Please enter your full name.");
            mBinding.fullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            mBinding.email.setError("Please enter your email.");
            mBinding.email.requestFocus();

        }

        //check if email is a valid one. if email does not match, throw an error.
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mBinding.email.setError("Please enter a valid email.");
            mBinding.email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mBinding.password.setError("Password required");
            mBinding.password.requestFocus();
            return;
        }

        //check if password valid. firebase itself dont allow password that is <6 characters
        if (password.length() < 6) {
            mBinding.password.setError("Minimum password length is 6 characters.");
            mBinding.password.requestFocus();
            return;
        }

        displayLoadingUi();
        registerWithFirebase(email, password, fullName);
    }

    private void displayLoadingUi() {
        //set visibility of progress bar to true once hit register button
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        dialog.setCancelable(false);
        dialog.setMessage("Creating user...");
        dialog.show();
    }

    private void registerWithFirebase(String email, String password, String fullName) {
        //push user info to firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.sendEmailVerification();
                        registerDisplayName(fullName, user);
                        Snackbar snackbar = Snackbar.make(mBinding.getRoot(), "Check your email to verify your account.", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        finish();
                        startActivity(new Intent(this, LoginActivity.class));
                    } else {
                        // User already exists in Firebase Auth
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Snackbar snackbar = Snackbar.make(mBinding.getRoot(), "User already exists. Please login instead.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else { // For unhandled errors
                            Snackbar snackbar = Snackbar.make(mBinding.getRoot(), "Failed to register, error: " + task.getException().toString(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.dismiss();
                    }
                });
    }

    private void registerDisplayName(String fullName, FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(fullName).build();
        user.updateProfile(profileUpdates);
    }
}