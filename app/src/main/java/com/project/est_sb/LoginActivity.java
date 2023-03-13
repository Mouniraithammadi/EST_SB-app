package com.project.est_sb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private DataBaseHLPR databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        databaseHelper = new DataBaseHLPR(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                boolean loginSuccessful = databaseHelper.checkUser(username, password);
               login( loginSuccessful );
            }
        });
    }
    public void login(boolean loginSuccessful){
        if (loginSuccessful) {
            // Navigate to the next activity
            Intent intent = new Intent( this,  MainActivity.class );
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }
    public void openRegistrationActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
