package com.vsc.vidasemcancer.Models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * The type Water.
 */
public class Water extends RealmObject {

    @PrimaryKey
    private String date;
    private int objective;
    private int currentLevel;
    private Date dDate;
    @Ignore
    private int sessionId;

    /**
     * Instantiates a new Water.
     */
    public Water() {

    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDDate(Date date) {
        this.dDate = date;
    }

    public Date getdDate() {
        return dDate;
    }

    /**
     * Gets objective.
     *
     * @return the objective
     */
    public int getObjective() {
        return objective;
    }

    /**
     * Sets objective.
     *
     * @param objective the objective
     */
    public void setObjective(int objective) {
        this.objective = objective;
    }

    /**
     * Gets current level.
     *
     * @return the current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets current level.
     *
     * @param currentLevel the current level
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
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


}
