package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import ru.skypro.homework.dto.CommentDtoOut;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

import java.security.Principal;

@RestController
@RequestScope
@RequestMapping("ads/{adId}/comments")
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;
    private final Principal principal;

    public CommentController(CommentService commentService, Principal principal) {
        this.commentService = commentService;
        this.principal = principal;
    }

    @GetMapping()
    public ResponseEntity<Comments> getComments(@PathVariable Integer adId){
        logger.info("The get all ad comments method is called.");
        return ResponseEntity.ok(commentService.findComments(adId));
    }

    @PostMapping()
    public ResponseEntity<CommentDtoOut> addComment(@PathVariable Integer adId,
                                                    @RequestBody CreateOrUpdateComment comment){
        logger.info("The comment creation method is called.");
        return ResponseEntity.ok(commentService.createComment(adId, comment, principal.getName()));
    }

    @PatchMapping("{commentId}")
    public ResponseEntity<CommentDtoOut> updateComment(@PathVariable Integer adId,
                                                       @PathVariable Integer commentId,
                                                       @RequestBody CreateOrUpdateComment comment){
        logger.info("The comment update method is called.");
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, comment,  principal.getName()));
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId,
                                           @PathVariable Integer commentId){
        logger.info("The comment delete method is called.");
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
