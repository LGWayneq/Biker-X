package com.example.bikerx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikerx.databinding.ActivityEmailLoginBinding;
import com.example.bikerx.databinding.ActivityLoginBinding;
import com.example.bikerx.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ActivityEmailLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        mBinding = ActivityEmailLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.register.setOnClickListener(this);
        mBinding.signIn.setOnClickListener(this);
        mBinding.forgotPassword.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //take user to RegisterUser page when click register on Email Login
            case R.id.register:
                startActivity(new Intent(EmailLoginActivity.this, RegisterUserActivity.class));
                break;
            //if user clicks login button on EmailLogin page, call userLogin() function below
            case R.id.signIn:
                userLogin();
                break;
            //if user clicks "forgotPassword" TextView on Email login page, lead user to ForgotPassword Page to reset password
            case R.id.forgotPassword:
                startActivity(new Intent(EmailLoginActivity.this, ForgotPasswordActivity.class));
                break;
        }
    }

    //when user clicks login button on EmailLogin page after filling up their email and password
    private void userLogin() {
        String email = mBinding.email.getText().toString().trim();
        String password = mBinding.password.getText().toString().trim();

        if (email.isEmpty()) {
            mBinding.email.setError("Email required");
            mBinding.email.requestFocus();
            return;

        }

        //check if email is a valid one
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mBinding.email.setError("Please provide valid email");
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
            mBinding.password.setError("Minimum password length is 6 characters");
            mBinding.password.requestFocus();
            return;
        }

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        dialog.setCancelable(false);
        dialog.setMessage("Logging you in...");
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    //check if email address has been verified by the user or not
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    //if user email is verified, direct them to home fragment
                    if (user.isEmailVerified()) {
                        //direct user to main activity once user logs in successfully with correct email and password
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(EmailLoginActivity.this, MainActivity.class));

                    } else {
                        //send email verification link to user
                        dialog.dismiss();
                        user.sendEmailVerification();
                        Toast.makeText(EmailLoginActivity.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                    }


                } else {
                    dialog.dismiss();
                    Toast.makeText(EmailLoginActivity.this, "Failed to login. check credentials", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}