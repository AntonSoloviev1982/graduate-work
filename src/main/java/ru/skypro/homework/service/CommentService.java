package ru.skypro.homework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdComment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          CommentMapper commentMapper,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public Comment createComment(Integer adId,
                                 CreateOrUpdateComment createOrUpdateComment,
                                 String userName){
        logger.info("Create comment.");
        return commentMapper.toDto(
                commentRepository.save(
                        commentMapper.toEntity(createOrUpdateComment, adId, getCurrentUser(userName).getId())));
    }

    @Transactional
    public Comments findComments(Integer adId){
        logger.info("Find all comments.");
        return  commentMapper.toComments(commentRepository.findAllByAdId(adId));
    }

    @Transactional
    public Comment updateComment(Integer adId,
                                 Integer commentId,
                                 CreateOrUpdateComment createOrUpdateComment,
                                 String userName){
        logger.info("Update comment.");
        AdComment oldComment = commentRepository.findById(commentId).
                orElseThrow(() -> new EntityNotFoundException("Comment not found."));
        if(!oldComment.getAd().getId().equals(adId)){
            throw new EntityNotFoundException("You can't change the ad in a comment");
        }
        oldComment.setText(createOrUpdateComment.getText());
        oldComment.setCreatedAt(LocalDateTime.now());
        oldComment.setUser(getCurrentUser(userName));
        return commentMapper.toDto(commentRepository.save(oldComment));
    }

    @Transactional
    public void deleteComment(Integer adId, Integer commentId, String userName){
        logger.info("Delete comment.");
        AdComment deletedComment = commentRepository.findById(commentId).
                orElseThrow(() -> new EntityNotFoundException("Comment not found."));
        if(!deletedComment.getAd().getId().equals(adId)){
            throw new EntityNotFoundException("Comment not found.");
        }
        commentRepository.delete(deletedComment);
    }

    private User getCurrentUser(String userName){
        return userRepository.findByUsername(userName).
                orElseThrow(() -> new UserNotFoundException("User not found."));
    }

}
