package com.example.lcapitulino.nunavikhomeservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreateProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_provider);
    }

    private boolean buttonIsChecked(int id) {
        View button = findViewById(id);
        return ((RadioButton) button).isChecked();
    }

    private String readText(int resource) {
        EditText Text = (EditText) findViewById(resource);
        String ret = Text.getText().toString();
        if (ret.isEmpty())
            throw new IllegalArgumentException("Required field is missing in " + Text.getHint().toString());
        return ret;
    }

    public void onClickAddProvider(View view) {
        String username, password, fullName, address, phone, companyName, description;

        try {
            username = readText(R.id.ProviderUsername);
            password = readText(R.id.ProviderPassword);
            fullName = readText(R.id.ProviderFullName);
            address = readText(R.id.ProviderAddress);
            phone = readText(R.id.ProviderPhoneNumber);
            companyName = readText(R.id.ProviderCompanyName);
            description = readText(R.id.ProviderDescription);
        } catch (IllegalArgumentException ex) {
            MyToast.display(getApplicationContext(), ex.getMessage());
            return;
        }

        ServiceProvider sProvider = new ServiceProvider(fullName, username, password);
        sProvider.setAddress(address);
        sProvider.setPhoneNumber(phone);
        sProvider.setCompanyName(companyName);
        sProvider.setDescription(description);
        sProvider.setLicensed(buttonIsChecked(R.id.radio1));

        /* Add Service Provider to the system */
        HomeService hService = HomeService.getInstance();
        try {
            hService.addServiceProvider(sProvider);
            finish();
            MyToast.display(getApplicationContext(), "User " + sProvider.getUserName() + " Added");
        } catch (IllegalArgumentException ex) {
            MyToast.display(getApplicationContext(), ex.getMessage());
        }
    }
}
