package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText handleInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.etUsername);
        handleInput = findViewById(R.id.etHandle);
        emailInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        signupBtn = findViewById(R.id.btnSignup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = new ParseUser();

                user.setUsername(usernameInput.getText().toString().trim());
                user.put("handle", handleInput.getText().toString().trim());
                user.setEmail(emailInput.getText().toString().trim());
                user.setPassword(passwordInput.getText().toString().trim());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(SignupActivity.this, "Success!", Toast.LENGTH_LONG);
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "One or more fields are insufficient.", Toast.LENGTH_LONG);
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}
