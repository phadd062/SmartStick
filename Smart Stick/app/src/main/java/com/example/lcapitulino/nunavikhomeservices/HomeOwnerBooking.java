package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class HomeOwnerBooking extends AppCompatActivity {
    private Service service;
    private ServiceProvider provider;
    private HomeOwner homeOwner;
    private TextView textViewName;
    private TextView textViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_booking);

        HomeService homeService = HomeService.getInstance();
        Intent intent = getIntent();
        service = homeService.searchServiceByName(intent.getStringExtra("service-name"));
        homeOwner = homeService.searchHomeOwner(intent.getStringExtra("homeowner-username"));
        provider = homeService.findProvider(intent.getStringExtra("provider-username"));

        textViewName = (TextView) findViewById(R.id.BookingTextViewName);
        textViewTime = (TextView) findViewById(R.id.BookingTextViewTime);

        textViewName.setText("Booking for service " + service.getName() + " with a rate of " + service.getRateString());
        textViewTime.setText("Available at the following times " + provider.AvaibilityToString());
    }

    private boolean buttonIsChecked(int id) {
        View button = findViewById(id);
        return ((RadioButton) button).isChecked();
    }

    public void onClickBookIt(View view) {
        Availability.dayOfWeek day = null;
        Availability.timeOfDay time = null;

        if (buttonIsChecked(R.id.BookingRadioMorning))
            time = Availability.timeOfDay.MORNING;
        if (buttonIsChecked(R.id.BookingRadioAfternoon))
            time = Availability.timeOfDay.AFTERNOON;
        if (buttonIsChecked(R.id.BookingRadioEvening))
            time = Availability.timeOfDay.EVENING;
        if (time == null) {
            MyToast.display(getApplicationContext(), "Select time of day");
            return;
        }

        if (buttonIsChecked(R.id.BookingRadioMonday))
            day = Availability.dayOfWeek.MONDAY;
        if (buttonIsChecked(R.id.BookingRadioTuesday))
            day = Availability.dayOfWeek.TUESDAY;
        if (buttonIsChecked(R.id.BookingRadioWednesday))
            day = Availability.dayOfWeek.WEDNESDAY;
        if (buttonIsChecked(R.id.BookingRadioThursday))
            day = Availability.dayOfWeek.THURSDAY;
        if (buttonIsChecked(R.id.BookingRadioFriday))
            day = Availability.dayOfWeek.FRIDAY;
        if (buttonIsChecked(R.id.BookingRadioSaturday))
            day = Availability.dayOfWeek.SATURDAY;
        if (buttonIsChecked(R.id.BookingRadioSunday))
            day = Availability.dayOfWeek.SUNDAY;
        if (day == null) {
            MyToast.display(getApplicationContext(), "Select day of week");
            return;
        }

        Availability bookingTime = new Availability(time, day);

        try {
            Booking booking = new Booking(service, provider, bookingTime);
            homeOwner.addBooking(booking);
        } catch (IllegalArgumentException ex) {
            MyToast.display(getApplicationContext(), "Failed to book: " + ex.getMessage());
            return;
        }

        finish();
        MyToast.display(getApplicationContext(), "Booked");
    }
}
