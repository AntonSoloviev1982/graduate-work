package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@RestController
@RequestMapping("/ads/{adId}/comments")
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @GetMapping()
    public ResponseEntity<Comments> getComments(@PathVariable Integer adId){
        logger.info("The get all ad comments method is called.");
        return ResponseEntity.ok(new Comments());
    }

    @PostMapping()
    public ResponseEntity<Comment> addComment(@PathVariable Integer adId, @RequestBody CreateOrUpdateComment comment){
        logger.info("The comment creation method is called.");
        return ResponseEntity.ok(new Comment());
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer adId, @PathVariable Integer commentId,@RequestBody CreateOrUpdateComment comment){
        logger.info("The comment update method is called.");
        return ResponseEntity.ok(new Comment());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId){
        logger.info("The comment delete method is called.");
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
