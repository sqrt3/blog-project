package com.estsoft.springproject.blog.domain.dto;

import com.estsoft.springproject.blog.domain.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWithArticleResponse {
    private Long commentId;
    private ArticleResponse article;
    private String body;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public CommentWithArticleResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.article = new ArticleResponse(comment.getArticle());
        this.body = comment.getBody();
        this.createdAt = comment.getCreatedAt();
    }
}
