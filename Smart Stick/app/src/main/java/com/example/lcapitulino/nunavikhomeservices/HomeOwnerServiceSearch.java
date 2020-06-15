package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeOwnerServiceSearch extends AppCompatActivity {
    private HomeService hService;
    private EditText searchBox;
    private ArrayList<ServiceProvider> sProviderList = null;
    private ListView searchRes;
    private TextView searchStatus;
    private ServiceSearch lastSearch;
    private HomeOwner homeOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_service_search);

        Intent intent = getIntent();
        hService = HomeService.getInstance();
        homeOwner = hService.searchHomeOwner(intent.getStringExtra("username"));
        searchBox = (EditText) findViewById(R.id.HomeOwnerSearchBox);
        searchRes = (ListView) findViewById(R.id.HomeOwnerSearchResults);
        searchStatus = (TextView) findViewById(R.id.HomeOwnerSearchStatus);
    }

    static ArrayList<String> getLabelList(ServiceSearch serviceSearch) {
        ArrayList<String> labelList = new ArrayList<String>();

        for (ServiceProvider sProvider : serviceSearch.getProvidersSearch()) {
            String str = " provider: " + sProvider.getUserName() + " - hourly rate: " + serviceSearch.getServiceRateString() + "$ - Rating avg: " + sProvider.getRatingAvg();
            labelList.add(str);
        }

        return labelList;
    }

    private void updateProviderList() {
        final ArrayList<String> list = getLabelList(lastSearch);
        if (list.size() == 0) {
            searchStatus.setText("no results for this search");
            return;
        }
        searchStatus.setText("resutls for service " + lastSearch.getServiceName());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
        final ListView listView = searchRes;
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceProvider provider = lastSearch.getProviderAt(position);
                Intent intent = new Intent(getApplicationContext(), HomeOwnerBooking.class);
                intent.putExtra("provider-username", provider.getUserName());
                intent.putExtra("service-name", lastSearch.getServiceName());
                intent.putExtra("homeowner-username", homeOwner.getUserName());
                startActivity(intent);

                return true;
            }
        });
    }

    private boolean buttonIsChecked(int id) {
        View button = findViewById(id);
        if (button == null)
            return false;
        return ((RadioButton) button).isChecked();
    }

    private Availability getBookingTime() {
        Availability.dayOfWeek day = Availability.dayOfWeek.NONE;
        Availability.timeOfDay time = Availability.timeOfDay.NONE;


        if (buttonIsChecked(R.id.HomeOwnerSearchMorning))
            time = Availability.timeOfDay.MORNING;
        if (buttonIsChecked(R.id.HomeOnwerSearchAfternoon))
            time = Availability.timeOfDay.AFTERNOON;
        if (buttonIsChecked(R.id.HomeOwnerSearchEvening))
            time = Availability.timeOfDay.EVENING;

        if (buttonIsChecked(R.id.HomeOwnerSearchMonday))
            day = Availability.dayOfWeek.MONDAY;
        if (buttonIsChecked(R.id.HomeOwnerSearchTuesday))
            day = Availability.dayOfWeek.TUESDAY;
        if (buttonIsChecked(R.id.HomeOwnerSearchWednesday))
            day = Availability.dayOfWeek.WEDNESDAY;
        if (buttonIsChecked(R.id.HomeOwnerSearchThursday))
            day = Availability.dayOfWeek.THURSDAY;
        if (buttonIsChecked(R.id.HomeOwnerSearchFriday))
            day = Availability.dayOfWeek.FRIDAY;
        if (buttonIsChecked(R.id.HomeOwnerSearchSaturday))
            day = Availability.dayOfWeek.SATURDAY;
        if (buttonIsChecked(R.id.HomeOwnerSearchSunday))
            day = Availability.dayOfWeek.SUNDAY;

        return new Availability(time, day);
    }

    private int getRating() {
        EditText ratingText = (EditText) findViewById(R.id.HomeOwnerSearchRating);
        String ratingStr = ratingText.getText().toString();
        if (ratingStr.isEmpty())
            return -1;
        return Integer.valueOf(ratingStr);
    }

    public void onClickSearchServices(View view) {
        searchRes.setAdapter(null);

        String name = searchBox.getText().toString();
        if (name.isEmpty()) {
            MyToast.display(getApplicationContext(), "Service name can't be empty");
            return;
        }

        Service service = hService.searchServiceByName(name);
        if (service == null) {
            return;
        }

        if (service.getProviderList().size() == 0)
            return;

        lastSearch = new ServiceSearch(service, getBookingTime(), getRating());
        updateProviderList();
    }
}
