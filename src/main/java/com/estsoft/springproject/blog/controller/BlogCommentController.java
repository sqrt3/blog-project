package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.dto.AddCommentRequest;
import com.estsoft.springproject.blog.domain.dto.CommentResponse;
import com.estsoft.springproject.blog.domain.dto.CommentWithArticleResponse;
import com.estsoft.springproject.blog.domain.dto.UpdateCommentRequest;
import com.estsoft.springproject.blog.service.BlogCommentService;
import com.estsoft.springproject.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BlogCommentController {
    private final BlogService blogService;
    private final BlogCommentService blogCommentService;

    public BlogCommentController(BlogService blogService, BlogCommentService blogCommentService) {
        this.blogService = blogService;
        this.blogCommentService = blogCommentService;
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> findCommentById(@PathVariable Long commentId) {
        Comment comment = blogCommentService.findBy(commentId);
        if (comment != null)
            return ResponseEntity.ok(comment.toEntity());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentWithArticleResponse> writeCommentByArticleId(
            @PathVariable Long articleId,
            @RequestBody AddCommentRequest request) {
        if (!articleId.equals(request.getArticleId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Article article = blogService.findBy(articleId);
        if (article != null) {
            Comment savedComment = blogCommentService.saveComment(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CommentWithArticleResponse(savedComment));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId, @RequestBody UpdateCommentRequest request) {
        Comment comment = blogCommentService.update(commentId, request);
        return ResponseEntity.ok().body(comment.toEntity());
    }

    @DeleteMapping("/comments/{commentId}") // delete article
    public ResponseEntity<Void> deleteById(@PathVariable Long commentId) {
        Comment comment = blogCommentService.findBy(commentId);
        if (comment != null) {
            blogCommentService.deleteBy(commentId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
