package com.vsc.vidasemcancer.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {

    @JsonProperty("self")
    private Self[] self;

    @JsonProperty("collection")
    private Collection[] collection;

    @JsonProperty("about")
    private About[] about;

    @JsonProperty("author")
    private Author[] author;

    @JsonProperty("replies")
    private Replies[] replies;

    @JsonProperty("version-history")
    private VersionHistory[] version_history;

    @JsonProperty("https://api.w.org/featuredmedia")
    private FeaturedMedia[] featuredMedia;

    @JsonProperty("https://api.w.org/attachment")
    private Attachment[] attachment;

    @JsonProperty("https://api.w.org/term")
    private Term[] term;

    @JsonProperty("self")
    public Self[] getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(Self[] self) {
        this.self = self;
    }

    @JsonProperty("collection")
    public Collection[] getCollection() {
        return collection;
    }

    @JsonProperty("collection")
    public void setCollection(Collection[] collection) {
        this.collection = collection;
    }

    @JsonProperty("about")
    public About[] getAbout() {
        return about;
    }

    @JsonProperty("about")
    public void setAbout(About[] about) {
        this.about = about;
    }

    @JsonProperty("author")
    public Author[] getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Author[] author) {
        this.author = author;
    }

    @JsonProperty("replies")
    public Replies[] getReplies() {
        return replies;
    }

    @JsonProperty("replies")
    public void setReplies(Replies[] replies) {
        this.replies = replies;
    }

    @JsonProperty("version-history")
    public VersionHistory[] getVersion_history() {
        return version_history;
    }

    @JsonProperty("version-history")
    public void setVersion_history(VersionHistory[] version_history) {
        this.version_history = version_history;
    }

    @JsonProperty("https:\\/\\/api.w.org\\/featuredmedia")
    public FeaturedMedia[] getFeaturedMedia() {
        return featuredMedia;
    }

    @JsonProperty("https:\\/\\/api.w.org\\/featuredmedia")
    public void setFeaturedMedia(FeaturedMedia[] featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    @JsonProperty("https:\\/\\/api.w.org\\/attachment")
    public Attachment[] getAttachment() {
        return attachment;
    }

    @JsonProperty("https:\\/\\/api.w.org\\/attachment")
    public void setAttachment(Attachment[] attachment) {
        this.attachment = attachment;
    }

    @JsonProperty("https:\\/\\/api.w.org\\/term")
    public Term[] getTerm() {
        return term;
    }

    @JsonProperty("https:\\/\\/api.w.org\\/term")
    public void setTerm(Term[] term) {
        this.term = term;
    }
}
