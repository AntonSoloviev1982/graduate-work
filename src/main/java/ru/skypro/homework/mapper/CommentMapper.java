package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdComment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.NoSuchElementException;

@Component
public class CommentMapper {

    private final UserRepository userRepository;
    private final AdRepository adRepository;

    public CommentMapper(UserRepository userRepository, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
    }

    public AdComment toEntity(CreateOrUpdateComment comment, Integer adId, Integer userId){
        AdComment adComment = new AdComment();
        adComment.setText(comment.getText());
        adComment.setCreatedAt(LocalDateTime.now());
        adComment.setAd(adRepository.findById(adId).get());
        adComment.setUser(userRepository.findById(userId).get());
        return adComment;
    }

    public Comment toDto(AdComment adComment){
        Comment comment = new Comment();
        User user = adComment.getUser();
        comment.setAuthor(user.getId());
        comment.setAuthorImage(user.getImage());
        comment.setAuthorFirstName(user.getFirstName());
        comment.setCreatedAt(adComment.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli());
        comment.setPk(adComment.getId());
        comment.setText(adComment.getText());
        return comment;
    }

}
