package com.estsoft.springproject.blog.domain.dto;

import com.estsoft.springproject.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleContent {
    private String title;
    private String body;

    public Article toArticle() {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(body);
        return article;
    }

    @Override
    public String toString() {
        return "Content [title=" + title + ", body=" + body + "]\n";
    }
}
