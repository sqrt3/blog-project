package com.estsoft.springproject.blog.domain;

import org.junit.jupiter.api.Test;

class ArticleTest {

  @Test
  public void testArticle() {
    Article article = new Article("제목", "내용");
    Article articleBuilder = Article.builder()
        .title("제목1")
        .content("내용1")
        .build();
  }
}