package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdExtendedDtoOut;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service("CheckUserService")
public class CheckUserService {

    private final AdService adService;
    private final CommentRepository commentRepository;

    public CheckUserService(AdService adService, CommentRepository commentRepository) {
        this.adService = adService;
        this.commentRepository = commentRepository;
    }

    public String getUsernameByAd(int id) {
        AdExtendedDtoOut adExtendedDtoOut = adService.getAdById(id);
        return adExtendedDtoOut.getEmail();
    }

    public String getUsernameByComment(int id) {
        return commentRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("Comment not found")).
                getUser().getUsername();
    }

}
