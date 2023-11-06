package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

import java.security.Principal;

@RestController
@RequestMapping("ads")
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{adId}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable Integer adId){
        logger.info("The get all ad comments method is called.");
        return ResponseEntity.ok(commentService.findComments(adId));
    }

    @PostMapping("{adId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Integer adId,
                                              @RequestBody CreateOrUpdateComment comment,
                                              Principal principal){
        String currentUserName = principal.getName();
        logger.info("The comment creation method is called by user " + currentUserName);
        return ResponseEntity.ok(commentService.createComment(adId, comment, currentUserName));
    }

    @PatchMapping("{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @CheckUserService.getUsernameByComment(#commentId) == principal.username")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer adId,
                                                 @PathVariable Integer commentId,
                                                 @RequestBody CreateOrUpdateComment comment,
                                                 Principal principal){
        logger.info("The comment update method is called.");
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, comment, principal.getName()));
    }

    @DeleteMapping("{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @CheckUserService.getUsernameByComment(#commentId) == principal.username")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId,
                                           @PathVariable Integer commentId,
                                           Principal principal){
        logger.info("The comment delete method is called.");
        commentService.deleteComment(adId, commentId, principal.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /*
    private String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    } */

}
