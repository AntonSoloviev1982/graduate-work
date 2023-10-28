package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private LocalDateTime createdAt;
    @ManyToOne
    private Ad ad;
    @ManyToOne
    private User user;

    public Comment(String text, Ad ad, User user) {
        this.text = text;
        this.ad = ad;
        this.user = user;
    }

    public Comment() {
    }
}
