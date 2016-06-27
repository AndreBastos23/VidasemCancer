package com.vsc.vidasemcancer.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Term {

    @JsonProperty("taxonomy")
    private String taxonomy;

    @JsonProperty("embeddable")
    private boolean embeddable;

    @JsonProperty("href")
    private String href;

    @JsonProperty("taxonomy")
    public String getTaxonomy() {
        return taxonomy;
    }

    @JsonProperty("taxonomy")
    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    @JsonProperty("embeddable")
    public boolean isEmbeddable() {
        return embeddable;
    }

    @JsonProperty("embeddable")
    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    @JsonProperty("href")
    public String getHref() {
        return href;
    }

    @JsonProperty("href")
    public void setHref(String href) {
        this.href = href;
    }
}
