package com.example.springdataautomappingobjects_exercise.entities.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.springdataautomappingobjects_exercise.constants.Messages.*;

public class GameDTO {

    private String title;
    private BigDecimal price;
    private float size;
    private String trailer;
    private String imageURL;
    private String description;
    private LocalDate releaseDate;

    public GameDTO() {
    }

    public GameDTO(String title, BigDecimal price, float size, String trailer, String imageURL, String description, LocalDate releaseDate) {
        setTitle(title);
        setPrice(price);
        setSize(size);
        setTrailer(trailer);
        setImageURL(imageURL);
        setDescription(description);
        setReleaseDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.isBlank() || !Character.isUpperCase(title.charAt(0))) {
            throw new IllegalArgumentException(TITLE_UPPERCASE_LETTER_MESSAGE);
        }

        if (title.length() < 3 || title.length() > 100) {
            throw new IllegalArgumentException(TITLE_LENGTH_MESSAGE);
        }

        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(PRICE_NOT_POSITIVE_MESSAGE);
        }

        this.price = price;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        if (size < 0) {
            throw new IllegalArgumentException(SIZE_NOT_POSITIVE_MESSAGE);
        }

        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        if (trailer.isBlank()) {
            throw new IllegalArgumentException(TRAILER_NULL_MESSAGE);
        }

        this.trailer = trailer.substring(trailer.length() - 11);
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        if (imageURL.isBlank() || !imageURL.startsWith("http://") && !imageURL.startsWith("https://")) {
            throw new IllegalArgumentException(INVALID_THUMBNAIL_URL_MESSAGE);
        }

        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.isBlank() || description.length() < 20) {
            throw new IllegalArgumentException(DESCRIPTION_INVALID_LENGTH_MESSAGE);
        }

        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate == null) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }

        this.releaseDate = releaseDate;
    }
}
