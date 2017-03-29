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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    String pwd, rPwd, eid, post;
    EditText password, rPassword, email;
    Button signup;
    TextView signin;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();
        mDialog = new ProgressDialog(this);
        password = (EditText) findViewById(R.id.etPwd);
        rPassword = (EditText) findViewById(R.id.etRePwd);
        email = (EditText) findViewById(R.id.etEmail);
        signup = (Button) findViewById(R.id.bSignup);
        signin = (TextView) findViewById(R.id.tvSignin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Applying Listener to Signup Button
        signup.setOnClickListener(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        pwd = password.getText().toString();
        rPwd = rPassword.getText().toString();
        eid = email.getText().toString();

        if (pwd.equals(rPwd)) {
            mDialog.setTitle("Please Wait");
            mDialog.setMessage("Creating Account. . .");
            mDialog.show();
            mAuth.createUserWithEmailAndPassword(eid, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(SignupActivity.this, "Error occurred while creating account.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Password not matching.", Toast.LENGTH_SHORT).show();
        }
        mDialog.dismiss();
    }
}
