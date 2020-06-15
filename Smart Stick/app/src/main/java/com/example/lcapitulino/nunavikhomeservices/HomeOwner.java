package com.example.lcapitulino.nunavikhomeservices;

import java.util.ArrayList;

class HomeOwner extends User {
    private ArrayList<Booking> bookingList;

    public void addBooking(Booking newBooking) {
        for (Booking booking : bookingList) {
            if (booking.isEqual(newBooking))
                throw new IllegalArgumentException("Booking already exists");
        }

        bookingList.add(newBooking);
    }

    public Booking getBookingAt(int index) {
        return bookingList.get(index);
    }

    public ArrayList<String> bookingToLabelList() {
        ArrayList<String> ret = new ArrayList<String>();
        for (Booking booking : bookingList) {
            ret.add(booking.toString());
        }

        return ret;
    }

    @Override
    public String toString() {
        String ret = "";
        for (Booking booking : bookingList)
            ret += " " + booking.toString();

        return super.toString() + " " + ret;
    }

    public HomeOwner(String fullName, String userName, String password) {
        super(fullName, userName, password, Role.OWNER);
        bookingList = new ArrayList<Booking>();
    }
}
