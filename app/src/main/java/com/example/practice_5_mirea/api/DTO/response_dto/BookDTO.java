package com.example.practice_5_mirea.api.DTO.response_dto;

public class BookDTO {
    private int id;
    private String title;
    private String description;
    private int pageCount;
    private String excerpt;
    private String publishDate;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getPublishDate() {
        return publishDate;
    }
}
