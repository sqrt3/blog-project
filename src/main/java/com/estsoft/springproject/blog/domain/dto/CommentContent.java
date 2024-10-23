package com.estsoft.springproject.blog.domain.dto;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentContent {
    private Long postId;
    private String body;

    public Comment toComment() {
        Article article = new Article();
        article.setId(this.postId);
        return new Comment(article, this.body);
    }
}
