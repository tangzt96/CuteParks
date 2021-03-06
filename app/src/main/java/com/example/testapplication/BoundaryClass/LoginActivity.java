package com.example.testapplication.BoundaryClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Log in UI that authenticates user information with database
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Code here
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.loginSignupButton);


        /**
         * log in button to send both username and password into database for authentication.
         */
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.usernameEditText);
                EditText passwordEditText = findViewById(R.id.passwordEditText);
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty()){
                    usernameEditText.setHintTextColor(Color.RED);
                    usernameEditText.requestFocus();
                } else if(password.isEmpty()){
                    passwordEditText.setHintTextColor(Color.RED);
                    passwordEditText.requestFocus();
                }
                /**
                 * returns the result of log in action.
                 */
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please try again.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
