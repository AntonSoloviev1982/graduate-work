package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdComment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
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

    public AdComment toEntity(CreateOrUpdateComment createOrUpdateComment, Integer adId, Integer userId){
        AdComment adComment = new AdComment();
        adComment.setText(createOrUpdateComment.getText());
        adComment.setCreatedAt(LocalDateTime.now());
        adComment.setAd(adRepository.findById(adId).orElseThrow(() -> new EntityNotFoundException("Ad not found.")));
        adComment.setUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found.")));
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

    @Transactional
    public Comments toComments(List<AdComment> list) {
        int size = list.size();
        Comments comments = new Comments();
        List<Comment> newList = new ArrayList<>();
        comments.setCount(size);
        for (int i = 0; i < size; i++) {
            newList.add(toDto(list.get(i)));
        }
        comments.setResults(newList);
        return comments;
    }
}
