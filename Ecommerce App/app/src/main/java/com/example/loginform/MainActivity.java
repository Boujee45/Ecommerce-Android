package com.example.loginform;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button loginButton;
    TextView msgText, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        msgText = findViewById(R.id.MessageText);
        signupButton = findViewById(R.id.signupButton);

        loginButton.setOnClickListener(v -> loginUser());

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {

        String usernameText = username.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(passwordText)) {
            msgText.setText(getString(R.string.empty_fields));
            msgText.setVisibility(View.VISIBLE);
            return;
        }

        try {
            DBConn dbConn = new DBConn(MainActivity.this);

            // ADMIN LOGIN
            if ("Admin".equalsIgnoreCase(usernameText) && "1234".equals(passwordText)) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
                return;
            }

            // NORMAL USER LOGIN
            if (dbConn.checkUser(usernameText, passwordText)) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Success")
                        .setMessage(getString(R.string.login_success))
                        .setPositiveButton("OK", (dialog, which) -> {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("username", usernameText);
                            startActivity(intent);
                            finish();
                        })
                        .setCancelable(false)
                        .show();

            } else {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Invalid Credentials")
                        .setMessage(getString(R.string.invalid_credentials))
                        .setPositiveButton("OK", null)
                        .show();
            }

        } catch (Exception e) {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Login Failed")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}