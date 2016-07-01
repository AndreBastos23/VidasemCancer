package com.vsc.vidasemcancer.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.GregorianCalendar;
import java.util.List;


public class Post {

    @JsonProperty("id")
    private int id;

    @JsonProperty("date")
    private GregorianCalendar date;

    @JsonProperty("date_gmt")
    private GregorianCalendar date_gmt;

    @JsonProperty("guid")
    private Guid guid;

    @JsonProperty("modified")
    private GregorianCalendar modified;

    @JsonProperty("modified_gmt")
    private GregorianCalendar modified_gmt;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("type")
    private String type;

    @JsonProperty("link")
    private String link;

    @JsonProperty("title")
    private Title title;

    @JsonProperty("content")
    private Content content;

    @JsonProperty("excerpt")
    private Excerpt excerpt;

    @JsonProperty("author")
    private int author;

    @JsonProperty("featured_media")
    private int featured_media;

    @JsonProperty("comment_status")
    private String comment_status;

    @JsonProperty("ping_status")
    private String ping_status;

    @JsonProperty("sticky")
    private boolean sticky;

    @JsonProperty("format")
    private String format;

    @JsonProperty("categories")
    private List<Integer> categories;

    @JsonProperty("tags")
    private List<Integer> tags;

    @JsonProperty("_links")
    private Links _links;

    /**
     * Gets links.
     *
     * @return the links
     */
    @JsonProperty("_links")
    public Links get_links() {
        return _links;
    }

    /**
     * Sets links.
     *
     * @param _links the links
     */
    @JsonProperty("_links")
    public void set_links(Links _links) {
        this._links = _links;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    @JsonProperty("date")
    public GregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    @JsonProperty("date")
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    /**
     * Gets date gmt.
     *
     * @return the date gmt
     */
    @JsonProperty("date_gmt")
    public GregorianCalendar getDate_gmt() {
        return date_gmt;
    }

    /**
     * Sets date gmt.
     *
     * @param date_gmt the date gmt
     */
    @JsonProperty("date_gmt")
    public void setDate_gmt(GregorianCalendar date_gmt) {
        this.date_gmt = date_gmt;
    }

    /**
     * Gets guid.
     *
     * @return the guid
     */
    @JsonProperty("guid")
    public Guid getGuid() {
        return guid;
    }

    /**
     * Sets guid.
     *
     * @param guid the guid
     */
    @JsonProperty("guid")
    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    /**
     * Gets modified.
     *
     * @return the modified
     */
    @JsonProperty("modified")
    public GregorianCalendar getModified() {
        return modified;
    }

    /**
     * Sets modified.
     *
     * @param modified the modified
     */
    @JsonProperty("modified")
    public void setModified(GregorianCalendar modified) {
        this.modified = modified;
    }

    /**
     * Gets modified gmt.
     *
     * @return the modified gmt
     */
    @JsonProperty("modified")
    public GregorianCalendar getModified_gmt() {
        return modified_gmt;
    }

    /**
     * Sets modified gmt.
     *
     * @param modified_gmt the modified gmt
     */
    @JsonProperty("modified_gmt")
    public void setModified_gmt(GregorianCalendar modified_gmt) {
        this.modified_gmt = modified_gmt;
    }

    /**
     * Gets slug.
     *
     * @return the slug
     */
    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    /**
     * Sets slug.
     *
     * @param slug the slug
     */
    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets link.
     *
     * @return the link
     */
    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    /**
     * Sets link.
     *
     * @param link the link
     */
    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    @JsonProperty("title")
    public Title getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    @JsonProperty("title")
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * Gets excerpt.
     *
     * @return the excerpt
     */
    @JsonProperty("excerpt")
    public Excerpt getExcerpt() {
        return excerpt;
    }

    /**
     * Sets excerpt.
     *
     * @param excerpt the excerpt
     */
    @JsonProperty("excerpt")
    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    @JsonProperty("author")
    public int getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    @JsonProperty("author")
    public void setAuthor(int author) {
        this.author = author;
    }

    /**
     * Gets featured media.
     *
     * @return the featured media
     */
    @JsonProperty("featured_media")
    public int getFeatured_media() {
        return featured_media;
    }

    /**
     * Sets featured media.
     *
     * @param featured_media the featured media
     */
    @JsonProperty("featured_media")
    public void setFeatured_media(int featured_media) {
        this.featured_media = featured_media;
    }

    /**
     * Gets comment status.
     *
     * @return the comment status
     */
    @JsonProperty("comment_status")
    public String getComment_status() {
        return comment_status;
    }

    /**
     * Sets comment status.
     *
     * @param comment_status the comment status
     */
    @JsonProperty("comment_status")
    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    /**
     * Gets ping status.
     *
     * @return the ping status
     */
    @JsonProperty("ping_status")
    public String getPing_status() {
        return ping_status;
    }

    /**
     * Sets ping status.
     *
     * @param ping_status the ping status
     */
    @JsonProperty("ping_status")
    public void setPing_status(String ping_status) {
        this.ping_status = ping_status;
    }

    /**
     * Is sticky boolean.
     *
     * @return the boolean
     */
    @JsonProperty("sticky")
    public boolean isSticky() {
        return sticky;
    }

    /**
     * Sets sticky.
     *
     * @param sticky the sticky
     */
    @JsonProperty("sticky")
    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    /**
     * Gets format.
     *
     * @return the format
     */
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    /**
     * Sets format.
     *
     * @param format the format
     */
    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Gets categories.
     *
     * @return the categories
     */
    @JsonProperty("categories")
    public List<Integer> getCategories() {
        return categories;
    }

    /**
     * Sets categories.
     *
     * @param categories the categories
     */
    @JsonProperty("categories")
    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    @JsonProperty("tags")
    public List<Integer> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    @JsonProperty("tags")
    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }


}

