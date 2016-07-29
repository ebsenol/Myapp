package com.example.senolb.project.api_help;

/**
 * Created by senolb on 28/07/16.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Movie {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("imdb_code")
    @Expose
    private String imdbCode;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_long")
    @Expose
    private String titleLong;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("cast")
    @Expose
    private List<String> cast = new ArrayList<String>();
    @SerializedName("directors")
    @Expose
    private List<String> directors = new ArrayList<String>();
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("mpa_rating")
    @Expose
    private String mpaRating;
    @SerializedName("synopsis")
    @Expose
    private String synopsis;
    @SerializedName("yt_trailer_code")
    @Expose
    private String ytTrailerCode;
    @SerializedName("google_video")
    @Expose
    private String googleVideo;
    @SerializedName("background_image")
    @Expose
    private String backgroundImage;
    @SerializedName("small_cover_image")
    @Expose
    private String smallCoverImage;
    @SerializedName("medium_cover_image")
    @Expose
    private String mediumCoverImage;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("date_uploaded")
    @Expose
    private String dateUploaded;
    @SerializedName("date_uploaded_unix")
    @Expose
    private Integer dateUploadedUnix;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The imdbCode
     */
    public String getImdbCode() {
        return imdbCode;
    }

    /**
     *
     * @param imdbCode
     * The imdb_code
     */
    public void setImdbCode(String imdbCode) {
        this.imdbCode = imdbCode;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The titleLong
     */
    public String getTitleLong() {
        return titleLong;
    }

    /**
     *
     * @param titleLong
     * The title_long
     */
    public void setTitleLong(String titleLong) {
        this.titleLong = titleLong;
    }

    /**
     *
     * @return
     * The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     *
     * @param slug
     * The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     *
     * @return
     * The year
     */
    public Integer getYear() {
        return year;
    }

    /**
     *
     * @param year
     * The year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     *
     * @return
     * The rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The runtime
     */
    public Integer getRuntime() {
        return runtime;
    }

    /**
     *
     * @param runtime
     * The runtime
     */
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    /**
     *
     * @return
     * The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     * The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     *
     * @return
     * The cast
     */
    public List<String> getCast() {
        return cast;
    }

    /**
     *
     * @param cast
     * The cast
     */
    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    /**
     *
     * @return
     * The directors
     */
    public List<String> getDirectors() {
        return directors;
    }

    /**
     *
     * @param directors
     * The directors
     */
    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    /**
     *
     * @return
     * The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The mpaRating
     */
    public String getMpaRating() {
        return mpaRating;
    }

    /**
     *
     * @param mpaRating
     * The mpa_rating
     */
    public void setMpaRating(String mpaRating) {
        this.mpaRating = mpaRating;
    }

    /**
     *
     * @return
     * The synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     *
     * @param synopsis
     * The synopsis
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     *
     * @return
     * The ytTrailerCode
     */
    public String getYtTrailerCode() {
        return ytTrailerCode;
    }

    /**
     *
     * @param ytTrailerCode
     * The yt_trailer_code
     */
    public void setYtTrailerCode(String ytTrailerCode) {
        this.ytTrailerCode = ytTrailerCode;
    }

    /**
     *
     * @return
     * The googleVideo
     */
    public String getGoogleVideo() {
        return googleVideo;
    }

    /**
     *
     * @param googleVideo
     * The google_video
     */
    public void setGoogleVideo(String googleVideo) {
        this.googleVideo = googleVideo;
    }

    /**
     *
     * @return
     * The backgroundImage
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     *
     * @param backgroundImage
     * The background_image
     */
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     *
     * @return
     * The smallCoverImage
     */
    public String getSmallCoverImage() {
        return smallCoverImage;
    }

    /**
     *
     * @param smallCoverImage
     * The small_cover_image
     */
    public void setSmallCoverImage(String smallCoverImage) {
        this.smallCoverImage = smallCoverImage;
    }

    /**
     *
     * @return
     * The mediumCoverImage
     */
    public String getMediumCoverImage() {
        return mediumCoverImage;
    }

    /**
     *
     * @param mediumCoverImage
     * The medium_cover_image
     */
    public void setMediumCoverImage(String mediumCoverImage) {
        this.mediumCoverImage = mediumCoverImage;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The torrents


    /**
     *
     * @return
     * The dateUploaded
     */
    public String getDateUploaded() {
        return dateUploaded;
    }

    /**
     *
     * @param dateUploaded
     * The date_uploaded
     */
    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    /**
     *
     * @return
     * The dateUploadedUnix
     */
    public Integer getDateUploadedUnix() {
        return dateUploadedUnix;
    }

    /**
     *
     * @param dateUploadedUnix
     * The date_uploaded_unix
     */
    public void setDateUploadedUnix(Integer dateUploadedUnix) {
        this.dateUploadedUnix = dateUploadedUnix;
    }

}
