package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AdminServicesActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextRate;
    private HomeService hService;

    private String getServiceName() {
        String ret = editTextName.getText().toString();
        if (ret.isEmpty()) {
            MyToast.display(getApplicationContext(), "Name field can't be empty");
            return null;
        }
        return ret;
    }

    private String getServiceRate() {
        String ret = editTextRate.getText().toString();
        if (ret.isEmpty()) {
            MyToast.display(getApplicationContext(), "Rate field can't be empty");
            return null;
        }
        return ret;
    }

    private void clearName() {
        editTextName.getText().clear();
    }

    private void clearRate() {
        editTextRate.getText().clear();
    }

    private void clearAllFields() {
        clearName();
        clearRate();
    }

    private void updateViewList() {
        final ArrayList<String> list = hService.getLabelList();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
        ListView listView = (ListView) findViewById(R.id.AdminListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AdminEditServiceActivity.class);
                intent.putExtra("serviceLabel", list.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        editTextName = (EditText) findViewById(R.id.AdminServicesName);
        editTextRate = (EditText) findViewById(R.id.AdminServicesRate);
        hService = HomeService.getInstance();

        updateViewList();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateViewList();
    }

    public void onClickAddService(View view) {
        String serviceName = getServiceName();
        if (serviceName == null)
            return;

        String serviceRate = getServiceRate();
        if (serviceRate == null)
            return;

        Service serv = new Service(serviceName, Double.parseDouble(serviceRate));

        try {
            hService.serviceAdd(serv);
        } catch (IllegalArgumentException ex) {
            MyToast.display(getApplicationContext(), ex.getMessage());
            return;
        }

        clearAllFields();
        updateViewList();
        MyToast.display(getApplicationContext(), "Service added");
    }
}
