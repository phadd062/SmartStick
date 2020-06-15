package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HomeOwnerRating extends AppCompatActivity {
    ServiceProvider provider;
    HomeOwner homeOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_rating);

        HomeService homeService = HomeService.getInstance();
        Intent intent = getIntent();

        TextView serviceName = (TextView) findViewById(R.id.HomeOwnerRatingServiceName);
        serviceName.setText("Rating for service " + intent.getStringExtra("service-name"));

        TextView providerName = (TextView) findViewById(R.id.HomeOwnerRatingProviderName);
        providerName.setText("Rating provider " + intent.getStringExtra("provider-username"));
        provider = homeService.findProvider(intent.getStringExtra("provider-username"));

        homeOwner = homeService.searchHomeOwner(intent.getStringExtra("homeowner-username"));
    }

    public void onClickAddRating(View view) {
        EditText textRating = (EditText) findViewById(R.id.HomeOwnerRatingServiceRating);
        String strRating = textRating.getText().toString();
        if (strRating.isEmpty()) {
            MyToast.display(getApplicationContext(), "Rating can't be empty");
            return;
        }

        int userRating = Integer.valueOf(strRating);

        EditText textComment = (EditText) findViewById(R.id.HomeOwnerRatingServiceComment);

        try {
            Rating rating = new Rating(userRating, textComment.toString(), (User) homeOwner);
            provider.addRating(rating);
        } catch (IllegalArgumentException ex) {
            MyToast.display(getApplicationContext(), "Failed to add rating " + ex.getMessage());
            return;
        }

        finish();
    }
}