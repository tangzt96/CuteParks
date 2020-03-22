package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.testapplication.EntityClass.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Sign up UI to allow users to sign up and store account credentials into database
 */
public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final Database db = new Database();

        Button completeSignupButton = findViewById(R.id.completeSignupButton);
        final EditText signupNameEditText = findViewById(R.id.signupNameEditText);
        final EditText signupEmailEditText = findViewById(R.id.signupEmailEditText);
        final EditText signupPasswordTextEdit = findViewById(R.id.signupPasswordEditText);

        /**
         * Sign up button to send username, email,password into database.
         */
        completeSignupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = signupNameEditText.getText().toString();
                String email = signupEmailEditText.getText().toString();
                String password = signupPasswordTextEdit.getText().toString();

                if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
                    signupEmailEditText.setError("Please key in your email address!");
                    signupEmailEditText.requestFocus();
                    signupPasswordTextEdit.setError("Please key in your password!");
                    signupPasswordTextEdit.requestFocus();
                    return;
                } else if (name.isEmpty()){
                    signupNameEditText.setError("Please key in your name!");
                    signupNameEditText.requestFocus();
                } else if(email.isEmpty()){
                    signupEmailEditText.setError("Please key in your email address!");
                    signupEmailEditText.requestFocus();
                    return;
                } else if(password.isEmpty()){
                    signupPasswordTextEdit.setError("Please key in your password!");
                    signupPasswordTextEdit.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    try {
                                        User newUser = new User(task.getResult().getUser().getUid(), name, password, email);
                                        db.createUser(newUser).whenComplete((uid, error) -> {
                                            finish();
                                            Toast.makeText(SignupActivity.this, "You have successfully signed in!",
                                                    Toast.LENGTH_SHORT).show();
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignupActivity.this, "An error has occurred while creating the user.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignupActivity.this, "Please try again.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}
