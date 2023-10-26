package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")  //таблица с именем user создается, только если имя указать в ``
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
    @OneToMany(mappedBy = "users")
    private List<Ad> ads;

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getImage() {
        return image;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImage(String image) {
        this.image = image;
    }
}