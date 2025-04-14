package com.example.mobilalkalmazas_fejlesztes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();

    private static final int REGISTERKEY = 256;
    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int register_key = getIntent().getIntExtra("REGISTERKEY", 256);



        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordConfirmEditText);


        Log.i(LOG_TAG, "onCreate");

        mAuth= FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String userName = userNameEditText.getText().toString();
        String userEmail = userEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirm = passwordConfirmEditText.getText().toString();

        // Check for empty fields
        if (userEmail.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            Log.e(LOG_TAG, "Email or password fields are empty!");
            return; // Exit method to prevent crash
        }

        // Check if passwords match
        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "Passwords do not match!");
            return; // Exit method if validation fails
        }

        // Proceed with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "Registration successful!");
                    startHeartMonitoring();
                } else {
                    Log.e(LOG_TAG, "Registration failed: " + task.getException().getMessage());
                }
            }
        });
    }

    private void startHeartMonitoring() {
        Intent intent = new Intent(this, HeartMonitoringActivity.class);
        intent.putExtra("REGISTERKEY",REGISTERKEY);
        startActivity(intent);
    }
    public void cancel(View view) {
        finish();
    }

}