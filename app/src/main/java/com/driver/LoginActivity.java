package com.driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    String eid, pswd;
    EditText email, pwd;
    TextView signup;
    Button signin;
    int counter = 0;

    ProgressDialog mDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mDialog = new ProgressDialog(this);
        email = (EditText) findViewById(R.id.etLEmail);
        pwd = (EditText) findViewById(R.id.etLpwd);
        signin = (Button) findViewById(R.id.bSignin);
        signup = (TextView) findViewById(R.id.tvSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        email.setOnFocusChangeListener(this);
        pwd.setOnFocusChangeListener(this);

        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDialog.setTitle("Please Wait");
        mDialog.setMessage("Loging In. . .");
        eid = email.getText().toString();
        pswd = pwd.getText().toString();
        mAuth.signInWithEmailAndPassword(eid, pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDialog.dismiss();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (counter != 0) {
            if (v.getId() == R.id.etLEmail) {
                if (email.getText().toString().trim().length() < 1) {
                    email.setError("Enter your Email ID");
                }
            }
            if (v.getId() == R.id.etLpwd) {
                if (pwd.getText().toString().trim().length() < 6) {
                    pwd.setError("Password should be of minimum 6 characters");
                }
            }
        }
        counter++;
    }
}
