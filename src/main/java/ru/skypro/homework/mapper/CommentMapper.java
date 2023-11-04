package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

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
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAd(adRepository.findById(adId).orElseThrow(() -> new EntityNotFoundException("Ad not found.")));
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

    public Comments toComments(List<Comment> list) {
        int size = list.size();
        Comments comments = new Comments();
        List<CommentDtoOut> newList = new ArrayList<>();
        comments.setCount(size);
        for (int i = 0; i < size; i++) {
            newList.add(toDto(list.get(i)));
        }
        comments.setResults(newList);
        return comments;
    }
}
