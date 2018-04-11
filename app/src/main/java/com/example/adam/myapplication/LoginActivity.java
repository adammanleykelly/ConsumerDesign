package com.example.adam.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class LoginActivity extends AppCompatActivity {

    Button button_Login;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_Login = (Button) findViewById(R.id.viewCards);

        button_Login.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        LoginActivity1();}
                });

    }
    public void LoginActivity1()
    {
        Intent act1 = new Intent(this,HomeActivity.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
