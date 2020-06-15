package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProviderListAvail extends AppCompatActivity {
    ServiceProvider sProvider;

    public void genAvailList() {
        final ArrayList<String> list = sProvider.availabilityToLabelList();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
        final ListView listView = (ListView) findViewById(R.id.ProviderAvailListView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String label = list.get(position);
                if (sProvider.removeAvailabilityWithLabel(label))
                    MyToast.display(getApplicationContext(), "Availability removed");
                genAvailList();
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list_avail);

        HomeService hService = HomeService.getInstance();

        Intent intent = getIntent();
        String username = intent.getStringExtra("provider-username");
        sProvider = hService.findProvider(username);
        assert(sProvider != null);

        genAvailList();
    }


}
