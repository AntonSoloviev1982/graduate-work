package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.PasswordsNotEqualsException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService  {

//    @Value("${avatar.dir.path}")
//    private String avatarDir;

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;



    public void setPassword(NewPasswordDto newPasswordDto, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        if (!encoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new PasswordsNotEqualsException();
        }
        user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    public UserDto getInfoAboutAuthorizedUser(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.userToUserDto(user);
    }

    public UpdateUserDto setInfoAboutAuthorizedUser(UpdateUserDto updateUserDto, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return updateUserDto;
    }

    @Transactional
    public void setAvatar(MultipartFile image, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        try {
            user.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        userRepository.save(user);
    }

    public byte[] getAvatar(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username)).getImage();
    }

//    @Transactional
//    public void setAvatar(MultipartFile image, Principal principal) {
//        String username = principal.getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException(username));
//        try {                 // "C:/Users/Anton/IdeaProjects/graduate-work/src/main/java/ru/skypro/homework/images"
//            String uploadDir = avatarDir;
//            String fileName = image.getOriginalFilename();
//            String filePath = uploadDir + "/" + fileName;
//            user.setImage(filePath);
//            FileOutputStream fos = new FileOutputStream(filePath);
//            fos.write(image.getBytes());
//            fos.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//        userRepository.save(user);
//    }
//
//    public byte[] getAvatar(Principal principal) {
//        String username = principal.getName();
//        String filePath = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException(username)).getImage();
//        try {
//            FileInputStream fis = new FileInputStream(filePath);
//            byte[] imageBytes = fis.readAllBytes();
//            fis.close();
//            return imageBytes;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }

}
