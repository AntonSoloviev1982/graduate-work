package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDtoOut;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.ZoneOffset;

@Component
public class CommentMapper {

    private final UserRepository userRepository;
    private final AdRepository adRepository;

    public CommentMapper(UserRepository userRepository, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
    }

    public Comment toEntity(CreateOrUpdateComment createOrUpdateComment, Integer adId, Integer userId){
        Comment comment = new Comment();
        comment.setText(createOrUpdateComment.getText());
        comment.setAd(adRepository.findById(adId).get());
        comment.setUser(userRepository.findById(userId).get());
        return comment;
    }

    public CommentDtoOut toDto(Comment comment){
        CommentDtoOut commentDtoOut = new CommentDtoOut();
        User user = comment.getUser();
        commentDtoOut.setAuthor(user.getId());
        commentDtoOut.setAuthorImage(user.getImage());
        commentDtoOut.setAuthorFirstName(user.getFirstName());
        commentDtoOut.setCreatedAt(comment.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli());
        commentDtoOut.setPk(comment.getId());
        commentDtoOut.setText(comment.getText());
        return commentDtoOut;
    }

}
