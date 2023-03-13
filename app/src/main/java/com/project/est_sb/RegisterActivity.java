package com.project.est_sb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private DataBaseHLPR databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        databaseHelper = new DataBaseHLPR(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (username.isEmpty()) {
                    usernameEditText.setError("Username is required");
                    usernameEditText.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("Passwords do not match");
                    confirmPasswordEditText.requestFocus();
                    return;
                }

                boolean addUserSuccessful = databaseHelper.insertData(username, password);
                if (addUserSuccessful) {
                    Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "User creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
