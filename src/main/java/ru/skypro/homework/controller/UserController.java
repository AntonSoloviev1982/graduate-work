package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import java.security.Principal;


@RestController
@RequestMapping("users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto newPasswordDto,
                                         Principal principal) {
        userService.setPassword(newPasswordDto, principal);
        return ResponseEntity.ok().build();
        // необходима проверка на ошибку 401
        // необходима проверка на ошибку 403
    }

    @GetMapping("me")
    public ResponseEntity<UserDto> getInfoAboutAuthorizedUser(Principal principal) {
        return ResponseEntity.ok().body(userService.getInfoAboutAuthorizedUser(principal));
        // необходима проверка на ошибку 401
    }

    @PatchMapping("me")
    public ResponseEntity<UpdateUserDto> setInfoAboutAuthorizedUser(@RequestBody UpdateUserDto updateUserDto,
                                                                    Principal principal) {
        return ResponseEntity.ok().body(userService.setInfoAboutAuthorizedUser(updateUserDto, principal));
        // необходима проверка на ошибку 401
    }

    @PatchMapping(path = "me/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> setAvatar(@RequestPart("image") MultipartFile image,
                                       Principal principal) {
        userService.setAvatar(image, principal);
        return ResponseEntity.ok().build();
        // необходима проверка на ошибку 401
    }
}
