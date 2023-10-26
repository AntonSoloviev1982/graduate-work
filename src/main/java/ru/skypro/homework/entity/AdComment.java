package ru.skypro.homework.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Integer adId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private Integer userId;

    public AdComment(String text, Integer adId, Integer userId) {
        this.text = text;
        this.createdAt = LocalDateTime.now();
        this.adId = adId;
        this.userId = userId;
    }

    public AdComment() {
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public Integer getAdId() {
        return adId;
    }

    public Integer getUserId() {
        return userId;
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

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
