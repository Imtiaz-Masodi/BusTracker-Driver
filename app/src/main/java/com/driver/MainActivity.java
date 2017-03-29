package com.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    String userId;
    FirebaseAuth auth;
    EditText busNo;
    Button services, stopService;
    MyLocationService service;

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        services = (Button) findViewById(R.id.bService);
        if (MyLocationService.isServiceActive)
            services.setText("STOP SERVICE");
        else
            services.setText("STAT SERVICE");

        busNo = (EditText) findViewById(R.id.etBusNo);
        auth = FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid();

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyLocationService.isServiceActive) {
                    Intent intent = new Intent(MainActivity.this, MyLocationService.class);
                    intent.putExtra("busno",busNo.getText().toString());
                    intent.putExtra("uid",userId);
                    startService(intent);
                    services.setText("STOP SERVICE");
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        });

    }
}
