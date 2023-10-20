package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDto {
    private int adId;
    private int userId;
    private int commentId;
    private String text;

}
