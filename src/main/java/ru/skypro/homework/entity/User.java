package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "`user`")  //таблица с именем user создается, только если имя указать в ``
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @Lob
//    @Column(columnDefinition = "oid")
//    @Basic(fetch=FetchType.LAZY)  //Чтобы не читать зря - FetchType.LAZY
//    private byte [] image;

    private String image;

    @OneToMany(mappedBy = "user")
    private List<Ad> ads;
}