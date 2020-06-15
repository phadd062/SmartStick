package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private void fillTestData() {
        HomeService hService = HomeService.getInstance();

        /* add admin user */
        /*hService.adminCreate("admin"); */

        /* Create Service Provider users */
        ServiceProvider sProvider1 = new ServiceProvider("prov1", "prov1", "prov1");
        sProvider1.setLicensed(true);
        sProvider1.setPhoneNumber("12345");
        sProvider1.setDescription("luiz");
        sProvider1.setCompanyName("luiz");
        sProvider1.setAddress("luiz");
        sProvider1.addAvailability(new Availability(Availability.timeOfDay.MORNING, Availability.dayOfWeek.MONDAY));
        sProvider1.addAvailability(new Availability(Availability.timeOfDay.AFTERNOON, Availability.dayOfWeek.WEDNESDAY));
        hService.addServiceProvider(sProvider1);

        ServiceProvider sProvider2 = new ServiceProvider("prov2", "prov2", "prov2");
        sProvider2.setLicensed(true);
        sProvider2.setPhoneNumber("12345");
        sProvider2.setDescription("luiz");
        sProvider2.setCompanyName("luiz");
        sProvider2.setAddress("luiz");
        sProvider2.addAvailability(new Availability(Availability.timeOfDay.MORNING, Availability.dayOfWeek.TUESDAY));
        sProvider2.addAvailability(new Availability(Availability.timeOfDay.MORNING, Availability.dayOfWeek.THURSDAY));
        hService.addServiceProvider(sProvider2);

        /* Create Services */
        String names[] = { "plumbing", "painting", "heating" };
        double rate[] = { 1.5, 50.5, 100 };
        for (int idx = 0; idx < 3; idx++) {
            Service serv = new Service(names[idx], rate[idx]);
            hService.serviceAdd(serv);

            // Link service provider and the services
            serv.addServiceProvider(sProvider1);
            sProvider1.addService(serv);

            serv.addServiceProvider(sProvider2);
            sProvider2.addService(serv);
        }

        /* Create Homeowner */
        HomeOwner hOwner = new HomeOwner("owner", "owner", "owner");
        hService.addHomeOwner(hOwner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fillTestData();
    }

    public void onCreateAdmin(View view) {
        HomeService hService = HomeService.getInstance();
        if (!hService.adminExists()) {
            Intent intent = new Intent(getApplicationContext(), CreateAdminActivity.class);
            startActivity(intent);
        } else
            MyToast.display(getApplicationContext(), "Admin user already created");
    }

    public void onClickLogIn(View view) {
        Intent intent = new Intent(getApplicationContext(), LogInActvity.class);
        startActivity(intent);
    }

    public void onClickCreateProvider(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateProviderActivity.class);
        startActivity(intent);
    }

    public void onClickCreateOwner(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateOwnerActivity.class);
        startActivity(intent);
    }
}
