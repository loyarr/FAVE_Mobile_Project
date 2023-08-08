package com.example.aksub_projectmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aksub_projectmobile.R;
import com.example.aksub_projectmobile.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    TextView loginLink;
    Button registerButton;
    EditText usernameField, emailField, phoneField, passwordField, confirmPasswordField;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://mobile-project-da6de-default-rtdb.asia-southeast1.firebasedatabase.app/");

        usernameField = findViewById(R.id.et_username);
        emailField = findViewById(R.id.et_email);
        phoneField = findViewById(R.id.et_phone);
        passwordField = findViewById(R.id.et_password);
        confirmPasswordField = findViewById(R.id.et_confirm_password);
        registerButton = findViewById(R.id.register_btn);
        loginLink = findViewById(R.id.tv_login_link);

        loginLink.setOnClickListener( v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        registerButton.setOnClickListener( v -> {
            String username = usernameField.getText().toString();
            String email = emailField.getText().toString();
            String phone = phoneField.getText().toString();
            String password = passwordField.getText().toString();
            String confirmPassword = confirmPasswordField.getText().toString();

            if(username.length() < 4){
                Toast.makeText(this, "Username length be must at least 5 characters.", Toast.LENGTH_SHORT).show();
            }else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                Toast.makeText(this, "Please provide a valid email.", Toast.LENGTH_SHORT).show();
            }else if(password.length() < 7){
                Toast.makeText(this, "Password length be must at least 8 characters.", Toast.LENGTH_SHORT).show();
            }else if(!password.equals(confirmPassword)){
                Toast.makeText(this, "Password is mismatched.", Toast.LENGTH_SHORT).show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to register?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(this, "Failed to register :(", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                userRef = firebaseDatabase.getReference("users").child(mAuth.getCurrentUser().getUid());
                                userRef.setValue(new User(username, email, phone));
                                finish();
                            });
                            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            Toast.makeText(this, "Registration cancelled :(", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            }
        });
    }
}