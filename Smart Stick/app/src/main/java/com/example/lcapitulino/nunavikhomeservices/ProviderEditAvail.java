package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class ProviderEditAvail extends AppCompatActivity {
    protected ServiceProvider sProvider;

    private boolean buttonIsChecked(int id) {
        View button = findViewById(id);
        return ((RadioButton) button).isChecked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_edit_avail);

        Intent intent = getIntent();
        HomeService hService = HomeService.getInstance();
        sProvider = hService.findProvider(intent.getStringExtra("provider-username"));
    }

    public void onClickddAvailability(View view) {
        Availability.dayOfWeek day = null;
        Availability.timeOfDay time = null;

        if (buttonIsChecked(R.id.ProviderTimeMorning))
            time = Availability.timeOfDay.MORNING;
        if (buttonIsChecked(R.id.ProviderTimeAfternoon))
            time = Availability.timeOfDay.AFTERNOON;
        if (buttonIsChecked(R.id.ProviderTimeEvening))
            time = Availability.timeOfDay.EVENING;
        if (time == null) {
            MyToast.display(getApplicationContext(), "Select time of day");
            return;
        }

        if (buttonIsChecked(R.id.ProviderDayMon))
            day = Availability.dayOfWeek.MONDAY;
        if (buttonIsChecked(R.id.ProviderDayTues))
            day = Availability.dayOfWeek.TUESDAY;
        if (buttonIsChecked(R.id.ProviderDayWed))
            day = Availability.dayOfWeek.WEDNESDAY;
        if (buttonIsChecked(R.id.ProviderDayThurs))
            day = Availability.dayOfWeek.THURSDAY;
        if (buttonIsChecked(R.id.ProviderDayFriday))
            day = Availability.dayOfWeek.FRIDAY;
        if (buttonIsChecked(R.id.ProviderDaySaturday))
            day = Availability.dayOfWeek.SATURDAY;
        if (buttonIsChecked(R.id.ProviderDaySunday))
            day = Availability.dayOfWeek.SUNDAY;
        if (day == null) {
            MyToast.display(getApplicationContext(), "Select day of week");
            return;
        }

        Availability avail = new Availability(time, day);
        if (!sProvider.addAvailability(avail))
            MyToast.display(getApplicationContext(), "Availability already exists");
        else
            MyToast.display(getApplicationContext(), "Availability added");
    }

    public void onClickListAvailability(View view) {
        Intent intent = new Intent(getApplicationContext(), ProviderListAvail.class);
        intent.putExtra("provider-username", sProvider.getUserName());
        startActivity(intent);
    }
}
