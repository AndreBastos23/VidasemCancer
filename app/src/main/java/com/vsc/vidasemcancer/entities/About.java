package com.vsc.vidasemcancer.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type About.
 */
public class About {
    @JsonProperty("href")
    private String href;

    /**
     * Gets href.
     *
     * @return the href
     */
    @JsonProperty("href")
    public String getHref() {
        return href;
    }

    /**
     * Sets href.
     *
     * @param href the href
     */
    @JsonProperty("href")
    public void setHref(String href) {
        this.href = href;
    }

}
