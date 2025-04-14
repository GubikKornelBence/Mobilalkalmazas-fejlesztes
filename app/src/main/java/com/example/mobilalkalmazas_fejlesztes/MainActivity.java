package com.example.mobilalkalmazas_fejlesztes;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

@RequiresApi(api = Build.VERSION_CODES.S)
public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREFERENCES = MainActivity.class.getPackageName().toString();
    private static final int REGISTERKEY= 256;
    EditText userNameET;
    EditText passwordET;
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout first

        userNameET = findViewById(R.id.editTextUserName); // Initialize views
        passwordET = findViewById(R.id.editTextPassword);
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.i(LOG_TAG, "onCreate");
        mAuth = FirebaseAuth.getInstance();
    }


    public void login(View view) {
        String userNameStr = userNameET.getText().toString();
        String passwordStr = passwordET.getText().toString();

        if (userNameStr.isEmpty() || passwordStr.isEmpty()) {
            Log.e(LOG_TAG, "Username or password is empty!");
            return; // Exit method to prevent invalid login attempts
        }

        mAuth.signInWithEmailAndPassword(userNameStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "Login successful!");
                    Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    startHeartMonitoring();
                } else {
                    // Handle task failure
                    Exception exception = task.getException();
                    if (exception != null) {
                        Log.e(LOG_TAG, "Login failed: " + exception.getMessage());
                        Toast.makeText(MainActivity.this, "Login failed: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Log.e(LOG_TAG, "Login failed: Unknown error occurred.");
                        Toast.makeText(MainActivity.this, "Login failed: Unknown error occurred.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private void startHeartMonitoring() {
        Intent intent = new Intent(this, HeartMonitoringActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
    Intent intent = new Intent(this, RegisterActivity.class);
    intent.putExtra("REGISTERKEY",256);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName",userNameET.getText().toString() );
        editor.putString("password",passwordET.getText().toString() );
        editor.apply();

        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }
}