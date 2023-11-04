package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDtoOut;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final AdRepository adRepository;

    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          CommentMapper commentMapper,
                          AdRepository adRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    public CommentDtoOut createComment(Integer adId, CreateOrUpdateComment createOrUpdateComment, String userName){
        Integer userId = userRepository.findByUsername(userName).
                orElseThrow(() -> new EntityNotFoundException("User not found.")).getId();
        return commentMapper.toDto(commentRepository.save(commentMapper.toEntity(createOrUpdateComment, adId, userId)));
    }

    public Comments findComments(Integer adId){
        return  commentMapper.toComments(commentRepository.findAllByAdId(adId));
    }



}
