package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdComment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class CommentMapper {

    private final UserRepository userRepository;

    public CommentMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AdComment toEntity(CreateOrUpdateComment comment, Integer adId, Integer userId){
        AdComment adComment = new AdComment();
        adComment.setText(comment.getText());
        adComment.setCreatedAt(LocalDateTime.now());
        adComment.setAdId(adId);
        adComment.setUserId(userId);
        return adComment;
    }

    public Comment toDto(AdComment adComment){
        Comment comment = new Comment();
        Integer userId = adComment.getUserId();
        User user = userRepository.findById(userId).get();
        comment.setAuthor(userId);
        comment.setAuthorImage(user.getImage());
        comment.setAuthorFirstName(user.getFirstName());
        comment.setCreatedAt(adComment.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli());
        comment.setPk(adComment.getId());
        comment.setText(adComment.getText());
        return comment;
    }

}
