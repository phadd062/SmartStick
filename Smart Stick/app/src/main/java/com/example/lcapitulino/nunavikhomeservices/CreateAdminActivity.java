package com.example.lcapitulino.nunavikhomeservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admin);
    }

    public void onClickCreateAdmin(View view) {
        EditText editTextPass = (EditText) findViewById(R.id.createAdminPassword);
        String password = editTextPass.getText().toString();
        if (password.isEmpty()) {
            MyToast.display(getApplicationContext(), "password can't be empty");
            return;
        }

        /* Add admin user to the system */
        HomeService hService = HomeService.getInstance();
        hService.adminCreate(password);

        finish();
    }
}
