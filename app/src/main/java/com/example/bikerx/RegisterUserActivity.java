package com.example.bikerx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.bikerx.databinding.ActivityLoginBinding;
import com.example.bikerx.databinding.ActivityRegisterUserBinding;
import com.example.bikerx.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterUserBinding mBinding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mBinding.registerUser.setOnClickListener(view -> registerUser());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                registerUser();
                break;
        }


    }

    private void registerUser() {
        //convert inputs in the register page to strings
        String email = mBinding.email.getText().toString().trim();
        String password = mBinding.password.getText().toString().trim();
        String fullName = mBinding.fullName.getText().toString().trim();
        int age = Integer.parseInt(mBinding.age.getText().toString());

        //validate inputs
        if (fullName.isEmpty()) {
            mBinding.fullName.setError("Please enter your full name.");
            mBinding.fullName.requestFocus();
            return;
        }

        if (mBinding.age.getText().toString().isEmpty()) {
            mBinding.age.setError("Please input your age.");
            mBinding.age.requestFocus();
            return;
        }

        if (age > 130) {
            mBinding.age.setError("Please input a valid age.");
            mBinding.age.requestFocus();
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

        //set visibility of progress bar to true once hit register button
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        dialog.setCancelable(false);
        dialog.setMessage("Creating user...");
        dialog.show();

        //push user info to firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(fullName, age, email);
                        Snackbar snackbar = Snackbar.make(mBinding.getRoot(), "Successfully registered user!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        finish();
                        startActivity(new Intent(this, LoginActivity.class));

                        //returns ID for registered user and pass the value to database with on click listener to check if its transferred succesfully
                        //check if task is successful(user registered)
//                        FirebaseDatabase.getInstance().getReference("Users")
//                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                .setValue(user).addOnCompleteListener(task1 -> {
//                                    Log.d("RegisterUserActivity", "in onComplete()");
//                                    if (task1.isSuccessful()) {
//                                        Snackbar snackbar = Snackbar.make(mBinding.getRoot(), "Successfully registered user!", Snackbar.LENGTH_SHORT);
//                                        snackbar.show();
//                                        mBinding.progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Snackbar snackbar = Snackbar.make(mBinding.getRoot(), "Failed to register user, try again!", Snackbar.LENGTH_SHORT);
//                                        snackbar.show();
//                                        mBinding.progressBar.setVisibility(View.GONE);
//
//                                    }
//                                });
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
}