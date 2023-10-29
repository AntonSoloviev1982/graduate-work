package ru.skypro.homework.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    private String title;       //4-32
    private int price;	        //0-10000000
    private String description; //8-64

    //Дима! Зачем придумывать спец место хранения изображения и ссылку на него
    //Можно ли хранить изображение со своим объектом?
    //Или мы уходим от этого варианта из экономии памяти,
    //чтобы hibernate не читал изображение вместе с остальными полями?
    //Чтобы не читать зря - поставил FetchType.LAZY
    @Lob
    @Column(columnDefinition = "oid")
    @Basic(fetch=FetchType.LAZY)
    //@Type(type = "org.hibernate.type.ImageType") вредная аннотация, с ней не работает
    private byte [] image;

    @OneToMany(mappedBy = "ad")
    private List<Comment> commentList;

}