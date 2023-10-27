package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private String title;       //4-32
    private int price;	        //0-10000000
    private String description; //8-64
    private String image;       //ссылка на фото (в базе или в файле)

    @JsonIgnore
    @OneToMany(mappedBy = "ad")
    private List<AdComment> adCommentList;

}