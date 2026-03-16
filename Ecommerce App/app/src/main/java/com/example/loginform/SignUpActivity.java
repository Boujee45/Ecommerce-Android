package com.example.loginform;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.view.View;
import android.text.TextUtils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_up);

        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText phoneNo = findViewById(R.id.phoneNo);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button signupButton = findViewById(R.id.signupButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        TextView msgText = findViewById(R.id.messageText);

        signupButton.setOnClickListener(v -> {
            String nameText = name.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String phoneNoText = phoneNo.getText().toString().trim();
            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if (TextUtils.isEmpty(nameText) || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(phoneNoText) || TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(passwordText)) {
                msgText.setText(getString(R.string.empty_fields));
                msgText.setVisibility(View.VISIBLE); // display error message
                return;
            }

            // create user account
            try {
                Users user = new Users(0, nameText, emailText, phoneNoText, usernameText, passwordText);
                DBConn dbConn = new DBConn(this);
                dbConn.addUser(user);

                new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("Success")
                    .setMessage(getString(R.string.signup_success))
                    .setPositiveButton("OK", (dialog, which) -> {
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setCancelable(false)
                    .show();
            } catch (Exception e) {
                msgText.setText(getString(R.string.signup_failed));
                msgText.setVisibility(View.VISIBLE);
            }
        });

        cancelButton.setOnClickListener(v -> {
            name.setText("");
            email.setText("");
            phoneNo.setText("");
            username.setText("");
            password.setText("");
        });
    }
}