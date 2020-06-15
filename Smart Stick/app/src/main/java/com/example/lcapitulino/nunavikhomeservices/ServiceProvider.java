package com.example.lcapitulino.nunavikhomeservices;

import java.util.ArrayList;

class ServiceProvider extends User {
    private String address;
    private String phoneNumber;
    private String companyName;
    private String description;
    private boolean licensed;
    private ServiceList sList = new ServiceList();
    private ArrayList<Availability> availList = new ArrayList<Availability>();
    private ArrayList<Rating> ratingList = new ArrayList<Rating>();

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isLicensed() {
        return licensed;
    }

    public boolean isEquals(ServiceProvider sProvider) {
        return sProvider.getUserName().equals(this.getUserName());
    }

    public void addService(Service serv) {
        sList.add(serv);
    }

    public ArrayList<String> getLabelList() {
        return sList.getLabelList();
    }

    public Service findService(String name) {
        return sList.find(name);
    }

    public Service getServiceFromLabel(String label) {
        return sList.getServiceFromLabel(label);
    }

    public void removeService(Service serv) {
        sList.remove(serv);
    }

    @Override
    public String toString() {
        return super.toString() + " " + address + " " + phoneNumber + " " + companyName + " " + description + " " + licensed;
    }

    /* Availability methods */
    public boolean addAvailability(Availability newAvail) {
        for (Availability avail : availList) {
            if (newAvail.equalsAvail(avail))
                return false;
        }
        availList.add(newAvail);
        return true;
    }

    public ArrayList<String> availabilityToLabelList() {
        ArrayList<String> labelList = new ArrayList<String>();
        for (Availability avail : availList) {
            labelList.add(avail.toString());
        }
        return labelList;
    }

    public boolean removeAvailabilityWithLabel(String label) {
        for (Availability avail : availList) {
            if (label.equals(avail.toString())) {
                availList.remove(avail);
                return true;
            }
        }
        return false;
    }

    public boolean hasBookingTime(Availability bookingTime) {
        for (Availability avail : availList) {
            if (avail.equalsAvail(bookingTime))
                return true;
        }

        return false;
    }

    public boolean hasBookingDay(Availability bookingTime) {
        for (Availability avail : availList) {
            if (avail.getDay() == bookingTime.getDay())
                return true;
        }

        return false;
    }

    public String AvaibilityToString() {
        String ret = "";
        for (Availability avail : availList) {
            ret = ret.concat(" " + avail.toString());        }
        return ret;
    }

    /* Rating methods */
    public void addRating(Rating newRating) {
        for (Rating rating : ratingList) {
            if (rating.getUser().equals(newRating.getUser()))
                throw new IllegalArgumentException("user has already rated");
        }

        ratingList.add(newRating);
    }

    public double getRatingAvg() {
        double total = 0;
        int size = ratingList.size();
        if (size == 0)
            return 0;

        for (Rating rating : ratingList) {
            total += rating.getRating();
        }

        return total / ratingList.size();
    }

    public ServiceProvider(String fullName, String userName, String password) {
        super(fullName, userName, password, Role.PROVIDER);
    }
}