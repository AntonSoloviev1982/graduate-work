package ru.skypro.homework.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
public class AdComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(nullable = false , name = "ad_id")
    private Ad ad;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public AdComment(String text, Ad ad, User user) {
        this.text = text;
        this.createdAt = LocalDateTime.now();
        this.ad = ad;
        this.user = user;
    }

    public AdComment() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
