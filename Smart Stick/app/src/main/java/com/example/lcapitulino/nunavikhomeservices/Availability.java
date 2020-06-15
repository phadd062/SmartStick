package com.example.lcapitulino.nunavikhomeservices;

public class Availability {
    public enum timeOfDay { NONE, MORNING, AFTERNOON, EVENING };
    public enum dayOfWeek { NONE, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY };
    protected timeOfDay time;
    protected dayOfWeek day;

    public String timeOfdayToString(timeOfDay time) {
        switch (time) {
            case MORNING:
                return "Morning";
            case AFTERNOON:
                return "Afternoon";
            case EVENING:
                return "evening";
            default:
                return "unknown";
        }
    }

    public String dayOfWeekToString(dayOfWeek day) {
        switch (day) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
            default:
                return "unknown";
        }
    }

    @Override
    public String toString() {
        return dayOfWeekToString(day) + " - " + timeOfdayToString(time);
    }

    public dayOfWeek getDay() {
        return day;
    }

    public timeOfDay getTime() {
        return time;
    }

    public boolean isNone() {
        return (day == dayOfWeek.NONE && time == timeOfDay.NONE);
    }

    public boolean equalsAvail(Availability avail) {
        return (day == avail.getDay()) && (time == avail.getTime());
    }

    public Availability (timeOfDay time, dayOfWeek day) {
        this.time = time;
        this.day = day;
    }
}
