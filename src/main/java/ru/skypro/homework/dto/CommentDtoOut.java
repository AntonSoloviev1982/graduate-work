package ru.skypro.homework.dto;

import lombok.Getter;

@Getter
public class CommentDtoOut {

    private Integer author;


    private String authorImage;

    private String authorFirstName;

    private Long createdAt;

    private Integer pk;

    private String text;

    public CommentDtoOut(Integer author, String authorImage, String authorFirstName, Long createdAt, Integer pk, String text) {
        this.author = author;
        this.authorImage = authorImage;
        this.authorFirstName = authorFirstName;
        this.createdAt = createdAt;
        this.pk = pk;
        this.text = text;
    }

    public CommentDtoOut() {
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public void setText(String text) {
        this.text = text;
    }
}
