package com.example.lcapitulino.nunavikhomeservices;

public class Rating {
    private int rating;
    private String comment;
    private User user;

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getUser() {
        return user.getUserName();
    }

    public Rating(int rating, String comment, User user) {
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("rating must be between 1 and 5");
        this.rating = rating;
        this.comment = comment;
        this.user = user;
    }
}
