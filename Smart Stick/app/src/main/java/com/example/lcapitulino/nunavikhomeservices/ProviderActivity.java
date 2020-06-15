package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProviderActivity extends AppCompatActivity {
    HomeService hService;
    ServiceProvider sProvider;
    TextView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        hService = HomeService.getInstance();
        tView = findViewById(R.id.ProviderTextView2);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        sProvider = hService.findProvider(username);
        assert(sProvider != null);
    }

    public void onClickEditAvailability(View view) {
        Intent intent = new Intent(getApplicationContext(), ProviderEditAvail.class);
        intent.putExtra("provider-username", sProvider.getUserName());
        startActivity(intent);
    }

    protected ArrayList<String> mygetLabelList() {
        ArrayList<String> fullList = hService.getLabelList();
        ArrayList<String> servList = sProvider.getLabelList();
        ArrayList<String> retList = new ArrayList<String>();

        for (int i = 0; i < fullList.size(); i++) {
            boolean found = false;
            String label = fullList.get(i);

            for (int j = 0; j < servList.size(); j++) {
                if (label.equals(servList.get(j))) {
                    found = true;
                    break;
                }
            }

            if (!found)
                retList.add(label);
        }

        return retList;
    }

    public void onClickListAllServices(View view) {
        tView.setText("Hold click service name to add to my services");
        final ArrayList<String> list = mygetLabelList();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
        final ListView listView = (ListView) findViewById(R.id.ProviderListView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Service serv = hService.getServiceFromLabel(list.get(position));
                try {
                    sProvider.addService(serv);
                    serv.addServiceProvider(sProvider);
                } catch (IllegalArgumentException ex) {
                    Toast.makeText(getApplicationContext(), serv.getName() + " already added", Toast.LENGTH_LONG).show();
                    return true;
                }

                Toast.makeText(getApplicationContext(), serv.getName() + " added", Toast.LENGTH_LONG).show();
                onClickListAllServices(null);
                return true;
            }
        });
    }

    public void onClickListMyServices(View view) {
        tView.setText("Hold click service name to remove from my services");
        final ArrayList<String> list = sProvider.getLabelList();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
        ListView listView = (ListView) findViewById(R.id.ProviderListView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Service serv = sProvider.getServiceFromLabel(list.get(position));
                sProvider.removeService(serv);
                serv.removeServiceProvider(sProvider);
                Toast.makeText(getApplicationContext(), serv.getName() + " removed", Toast.LENGTH_LONG).show();
                onClickListMyServices(null);
                return true;
            }
        });
    }
}
