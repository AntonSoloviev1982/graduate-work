package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;

}
