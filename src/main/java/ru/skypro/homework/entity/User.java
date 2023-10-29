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
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image; //ссылка на фото (в базе или в файле)

    @OneToMany(mappedBy = "user")
    private List<Ad> ads;

}