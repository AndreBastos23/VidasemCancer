package com.vsc.vidasemcancer.entities;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The type Featured media.
 */
public class FeaturedMedia {

    @JsonProperty("href")
    private String href;

    @JsonProperty("embeddable")
    private boolean embeddable;

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

    /**
     * Gets replies.
     *
     * @return the replies
     */
    @JsonProperty("embeddable")
    public boolean getEmbeddable() {
        return embeddable;
    }

    /**
     * Sets replies.
     *
     * @param embeddable the embeddable
     */
    @JsonProperty("embeddable")
    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

}
