package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "`user`")  //таблица с именем user создается, только если имя указать в ``
@Entity
public class User {
    @Id
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