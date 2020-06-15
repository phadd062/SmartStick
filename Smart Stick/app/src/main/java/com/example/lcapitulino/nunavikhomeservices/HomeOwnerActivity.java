package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeOwnerActivity extends AppCompatActivity {
    HomeOwner hOwner;
    HomeService hService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeowner);

        String username = getIntent().getStringExtra("username");
        hService = HomeService.getInstance();
        hOwner = hService.searchHomeOwner(username);
    }

    public void onClickSearchServices(View view) {
        Intent intent = new Intent(getApplication(), HomeOwnerServiceSearch.class);
        intent.putExtra("username", hOwner.getUserName());
        startActivity(intent);
    }

    public void onClickListServices(View view) {
        final ArrayList<String> list = hOwner.bookingToLabelList();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
        final ListView listView = (ListView) findViewById(R.id.HomeOwnerListView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Booking booking = hOwner.getBookingAt(position);
                Intent intent = new Intent(getApplicationContext(), HomeOwnerRating.class);
                intent.putExtra("provider-username", booking.getProviderName());
                intent.putExtra("service-name", booking.getServiceName());
                intent.putExtra("homeowner-username", hOwner.getUserName());
                startActivity(intent);

                return true;
            }
        });
    }
}
