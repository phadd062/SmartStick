package com.example.lcapitulino.nunavikhomeservices;

public class Booking {
    private Service service;
    private ServiceProvider provider;
    private Availability bookingTime;

    public Service getService() {
        return service;
    }

    public ServiceProvider getProvider() {
        return provider;
    }

    public String getServiceName() {
        return service.getName();
    }

    public String getProviderName() {
        return provider.getUserName();
    }

    public Availability getTime() {
        return bookingTime;
    }

    public boolean isEqual(Booking b) {
        if (!service.getName().contentEquals(b.getServiceName()))
            return false;
        if (!provider.getUserName().contentEquals(b.getProviderName()))
            return false;
        return bookingTime.equalsAvail(b.getTime());
    }

    @Override
    public String toString() {
        return "service: " + service.getName() + " provider: " + provider.getUserName() + " time: " + bookingTime.toString();
    }

    public Booking(Service service, ServiceProvider provider, Availability bookingTime) {
        if (!provider.hasBookingTime(bookingTime))
            throw new IllegalArgumentException("No booking time available");
        this.service = service;
        this.provider = provider;
        this.bookingTime = bookingTime;
    }
}
