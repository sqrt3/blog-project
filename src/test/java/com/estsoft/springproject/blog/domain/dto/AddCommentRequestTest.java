package com.estsoft.springproject.blog.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddCommentRequestTest {


  @Test
  void testAddCommentRequest() {
    Long expectedArticleId = 1L;
    String expectedBody = "This is a comment.";

    AddCommentRequest addCommentRequest = new AddCommentRequest(expectedArticleId, expectedBody);

    assertEquals(expectedArticleId, addCommentRequest.getArticleId());
    assertEquals(expectedBody, addCommentRequest.getBody());
  }
}
