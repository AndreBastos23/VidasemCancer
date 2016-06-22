package com.vsc.vidasemcancer.Models;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * The type Meals.
 */
public class Meals extends RealmObject {

    @PrimaryKey
    private String date;
    private Boolean breakfast;
    private Boolean morningSnack;
    private Boolean lunch;
    private Boolean afternoonSnack;
    private Boolean dinner;
    private Date dDate;


    @Ignore
    private int sessionId;

    /**
     * Instantiates a new Meals.
     */
    public Meals() {

    }


    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets breakfast.
     *
     * @return the breakfast
     */
    public Boolean getBreakfast() {
        return breakfast;
    }

    /**
     * Sets breakfast.
     *
     * @param breakfast the breakfast
     */
    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    /**
     * Gets morning snack.
     *
     * @return the morning snack
     */
    public Boolean getMorningSnack() {
        return morningSnack;
    }

    /**
     * Sets morning snack.
     *
     * @param morningSnack the morning snack
     */
    public void setMorningSnack(Boolean morningSnack) {
        this.morningSnack = morningSnack;
    }

    /**
     * Gets lunch.
     *
     * @return the lunch
     */
    public Boolean getLunch() {
        return lunch;
    }

    /**
     * Sets lunch.
     *
     * @param lunch the lunch
     */
    public void setLunch(Boolean lunch) {
        this.lunch = lunch;
    }

    /**
     * Gets afternoon snack.
     *
     * @return the afternoon snack
     */
    public Boolean getAfternoonSnack() {
        return afternoonSnack;
    }

    /**
     * Sets afternoon snack.
     *
     * @param afternoonSnack the afternoon snack
     */
    public void setAfternoonSnack(Boolean afternoonSnack) {
        this.afternoonSnack = afternoonSnack;
    }

    /**
     * Gets dinner.
     *
     * @return the dinner
     */
    public Boolean getDinner() {
        return dinner;
    }

    /**
     * Sets dinner.
     *
     * @param dinner the dinner
     */
    public void setDinner(Boolean dinner) {
        this.dinner = dinner;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getdDate() {
        return dDate;
    }

    /**
     * Sets date.
     *
     * @param dDate the d date
     */
    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }

    /**
     * Gets session id.
     *
     * @return the session id
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Sets session id.
     *
     * @param sessionId the session id
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
