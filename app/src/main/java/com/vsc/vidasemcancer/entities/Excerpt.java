package com.vsc.vidasemcancer.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Excerpt {
    @JsonProperty("rendered")
    private String rendered;

    @JsonProperty("rendered")
    public String getRendered() {
        return rendered;
    }

    @JsonProperty("rendered")
    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}
