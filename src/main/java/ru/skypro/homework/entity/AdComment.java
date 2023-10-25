package ru.skypro.homework.entity;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class AdComment {
    @Id
    private Long id;
    private String text;

    @ManyToOne
    @JoinColumn(nullable = false , name = "ad_id")
    private Ad ad;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;



}
