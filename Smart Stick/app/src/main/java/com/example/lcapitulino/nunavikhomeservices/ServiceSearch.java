package com.example.lcapitulino.nunavikhomeservices;

import java.util.ArrayList;

public class ServiceSearch {
    private int ratingAvg;
    private Service service;
    private Availability bookingTime;
    private ArrayList<ServiceProvider> providerList;

    public String getServiceName() {
        return service.getName();
    }

    public String getServiceRateString() {
        return service.getRateString();
    }

    public ServiceProvider getProviderAt(int index) {
        return providerList.get(index);
    }

    public ArrayList<ServiceProvider> getAllProviders() {
        providerList = service.getProviderList();
        return providerList;
    }

    public ArrayList<ServiceProvider> getProviderByAvail() {
        providerList = new ArrayList<ServiceProvider>();
        for (ServiceProvider provider: service.getProviderList()) {
            if (provider.hasBookingTime(bookingTime))
                providerList.add(provider);
        }

        return providerList;
    }

    public ArrayList<ServiceProvider> getProviderByDay() {
        providerList = new ArrayList<ServiceProvider>();
        for (ServiceProvider provider: service.getProviderList()) {
            if (provider.hasBookingDay(bookingTime))
                providerList.add(provider);
        }

        return providerList;
    }

    public ArrayList<ServiceProvider> getProviderByRatingAvg() {
        providerList = new ArrayList<ServiceProvider>();
        for (ServiceProvider provider: service.getProviderList()) {
            if (provider.getRatingAvg() >= this.ratingAvg)
                providerList.add(provider);
        }

        return providerList;
    }

    public ArrayList<ServiceProvider> getProvidersSearch() {
        if (ratingAvg >= 1 && ratingAvg <= 5)
            return getProviderByRatingAvg();
        if (bookingTime.isNone())
            return getAllProviders();
        if (bookingTime.getDay() != Availability.dayOfWeek.NONE && bookingTime.getTime() == Availability.timeOfDay.NONE)
            return getProviderByDay();
        return getProviderByAvail();
    }

    public ServiceSearch(Service service, Availability bookingTime, int ratingAvg) {
        this.service = service;
        this.bookingTime = bookingTime;
        this.ratingAvg = ratingAvg;
        providerList = service.getProviderList();
    }
}