package com.helio.silentsecret.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.helio.silentsecret.R;

public class LikeDialogActivity extends BaseActivity
{

    TextView signup_button = null, signin_button = null, cancel_button = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_dialog);
        signup_button = (TextView) findViewById(R.id.signup_button);
        signin_button = (TextView) findViewById(R.id.signin_button);
        cancel_button = (TextView) findViewById(R.id.cancel_button);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LikeDialogActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LikeDialogActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
