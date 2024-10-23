package com.estsoft.springproject.blog.domain.dto;

import com.estsoft.springproject.blog.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCommentRequest {
    private Long articleId;
    private String body;

    public AddCommentRequest(Long articleId, String body) {
        this.articleId = articleId;
        this.body = body;
    }

    public Comment toEntity() {
        Comment comment = new Comment();
        comment.setBody(this.body);
        return comment;
    }
}
