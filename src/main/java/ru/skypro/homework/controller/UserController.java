package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.util.ValidationUtils;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private final ValidationUtils validationUtils;

    public UserController(ValidationUtils validationUtils) {
        this.validationUtils = validationUtils;
    }

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto newPassword) {
        validationUtils.isValid(newPassword);
        return ResponseEntity.ok().build();
        // нужно создать один маппер
        // необходима проверка на ошибки: 401 и 403
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getInfoAboutAuthorizedUser() {
        return ResponseEntity.ok(new UserDto());
        // нужно создать один маппер
        // необходима проверка на ошибку 401
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> setInfoAboutAuthorizedUser(@RequestBody UpdateUserDto updateUser) {
        validationUtils.isValid(updateUser);
        return ResponseEntity.ok(new UpdateUserDto());
        // нужно создать два маппера
        // необходима проверка на ошибку 401
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> setAvatar(@RequestBody String image) {
        return ResponseEntity.ok().build();
        // необходима проверка на ошибку 401
    }




}
