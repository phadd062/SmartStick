package com.example.lcapitulino.nunavikhomeservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdminEditServiceActivity extends AppCompatActivity {
    private Service currentService;
    private HomeService hService;
    private EditText editTextName;
    private EditText editTextRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_service);

        hService = HomeService.getInstance();

        String label = getIntent().getStringExtra("serviceLabel");
        currentService = hService.getServiceFromLabel(label);

        editTextName = (EditText) findViewById(R.id.EditServicesName);
        editTextName.setText(currentService.getName());

        editTextRate = (EditText) findViewById(R.id.EditServicesRate);
        editTextRate.setText(currentService.getRateString());
    }

    public void OnClickDeleteService(View view) {
        hService.serviceRemove(currentService.getName());
        MyToast.display(getApplicationContext(), "Service deleted");
        finish();
    }

    public void OnClickUpdateService(View view) {
        currentService.setName(editTextName.getText().toString());
        currentService.setRate(editTextRate.getText().toString());
        MyToast.display(getApplicationContext(), "Service updated");
        finish();
    }
}