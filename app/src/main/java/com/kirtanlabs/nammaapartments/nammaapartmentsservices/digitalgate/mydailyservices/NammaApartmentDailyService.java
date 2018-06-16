package com.kirtanlabs.nammaapartments.nammaapartmentsservices.digitalgate.mydailyservices;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * KirtanLabs Pvt. Ltd.
 * Created by Ashish Jha on 6/8/2018
 */

class NammaApartmentDailyService implements Serializable {


    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    private String dailyServiceType;
    private String fullName;
    private String phoneNumber;
    private String profilePhoto;
    private String timeOfVisit;
    private Boolean providedThings;
    private int rating;
    private String uid;
    private Map<String, Boolean> ownersUID = new HashMap<>();

    /* ------------------------------------------------------------- *
     * Constructors
     * ------------------------------------------------------------- */

    public NammaApartmentDailyService() {
    }

    NammaApartmentDailyService(String uid, String fullName, String phoneNumber, String profilePhoto, String timeOfVisit, boolean providedThings, int rating) {
        this.uid = uid;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
        this.timeOfVisit = timeOfVisit;
        this.providedThings = providedThings;
        this.rating = rating;
    }

    /* ------------------------------------------------------------- *
     * Getters
     * ------------------------------------------------------------- */

    public String getfullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getTimeOfVisit() {
        return timeOfVisit;
    }

    public Boolean getProvidedThings() {
        return providedThings;
    }

    public int getRating() {
        return rating;
    }

    public String getDailyServiceType() {
        return dailyServiceType;
    }

    public Map<String, Boolean> getOwnersUID() {
        return ownersUID;
    }

    public String getUID() {
        return uid;
    }

    /* ------------------------------------------------------------- *
     * Setters
     * ------------------------------------------------------------- */

    public void setDailyServiceType(String dailyServiceType) {
        this.dailyServiceType = dailyServiceType;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}
