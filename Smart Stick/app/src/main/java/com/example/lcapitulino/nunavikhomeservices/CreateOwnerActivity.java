package com.example.lcapitulino.nunavikhomeservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_owner);
    }
    private String readText(int resource) {
        EditText Text = (EditText) findViewById(resource);
        String ret = Text.getText().toString();
        if (ret.isEmpty())
            throw new IllegalArgumentException("Required field is missing in " + Text.getHint().toString());
        return ret;
    }

    public void onClickAddOwner(View view) {
        String username, password, fullName;

        try {
            username = readText(R.id.OwnerUsername);
            password = readText(R.id.OwnerPassword);
            fullName = readText(R.id.OwnerFullName);
        } catch (IllegalArgumentException ex) {
            MyToast.display(getApplicationContext(), ex.getMessage());
            return;
        }

        HomeOwner hOwner = new HomeOwner(fullName, username, password);

        /* Add HomeOwner to the system */
        HomeService hService = HomeService.getInstance();
        try {
            hService.addHomeOwner(hOwner);
            finish();
            MyToast.display(getApplicationContext(), "User " + hOwner.getUserName() + " Added");
        } catch (IllegalArgumentException ex) {
            MyToast.display(getApplicationContext(), ex.getMessage());
        }
    }
}
