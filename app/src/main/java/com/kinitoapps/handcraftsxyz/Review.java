package com.kinitoapps.handcraftsxyz;

/**
 * Created by HP INDIA on 02-Jun-18.
 */

public class Review {
    private String revName, revText;
    private int stars;

    public Review(String revName, String revText, int stars){
        this.revName = revName;
        this.revText = revText;
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public String getRevName() {
        return revName;
    }

    public String getRevText() {
        return revText;
    }

    public void setRevName(String revName) {
        this.revName = revName;
    }

    public void setRevText(String revText) {
        this.revText = revText;
    }

    public void setStars(short stars) {
        this.stars = stars;
    }
}

