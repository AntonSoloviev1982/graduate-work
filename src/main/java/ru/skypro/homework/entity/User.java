package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity(name = "users")  //таблица с именем user не создается
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