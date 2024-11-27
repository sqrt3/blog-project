package com.estsoft.springproject.blog.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.dto.UpdateArticleRequest;
import com.estsoft.springproject.blog.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BlogControllerTest {

  @InjectMocks
  BlogController blogController;

  @Mock
  BlogService blogService;

  MockMvc mockMvc;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
  }

  @Test
  void testSaveArticle() throws Exception {
    String title = "mock_title";
    String content = "mock_content";

    AddArticleRequest request = new AddArticleRequest(title, content);
    ObjectMapper objectMapper = new ObjectMapper();
    String requestJson = objectMapper.writeValueAsString(request);

    Mockito.when(blogService.saveArticle(any()))
        .thenReturn(new Article(title, content));

    ResultActions resultActions = mockMvc.perform(
        post("/api/articles")
            .content(requestJson)
            .contentType(MediaType.APPLICATION_JSON)
    );

    resultActions.andExpect(status().isCreated())
        .andExpect(jsonPath("title").value(request.getTitle()))
        .andExpect(jsonPath("content").value(request.getContent()));
  }

  @Test
  void testGetAllArticles() throws Exception {
    String title = "mock_title";
    String content = "mock_content";

    AddArticleRequest request = new AddArticleRequest(title, content);
    ObjectMapper objectMapper = new ObjectMapper();

    String requestJson = objectMapper.writeValueAsString(request);

    Mockito.when(blogService.saveArticle(any()))
        .thenReturn(new Article(title, content));

    for (int i = 0; i < 3; i++) {
      mockMvc.perform(
              post("/api/articles")
                  .content(requestJson)
                  .contentType(MediaType.APPLICATION_JSON)
          )
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.title").value(title))
          .andExpect(jsonPath("$.content").value(content));
    }

    mockMvc.perform(get("/api/articles"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));

    Mockito.verify(blogService, Mockito.times(3)).saveArticle(any());
  }

  @Test
  @Disabled("Test not available")
  void testDeleteArticle() throws Exception {
    for (int i = 1; i <= 3; i++) {
      ResultActions resultActions = mockMvc.perform(
          delete("/api/articles/{i}", i)
      );
      resultActions.andExpect(status().isBadRequest());
    }

    Mockito.verify(blogService, Mockito.times(3)).deleteBy(anyLong());
  }

  @Test
  void testFindArticleById() throws Exception {
    Long id = 1L;
    String title = "제목1";
    String content = "내용1";

    Mockito.when(blogService.findBy(1L))
        .thenReturn(new Article(title, content));

    ResultActions resultActions = mockMvc.perform(get("/api/articles/{id}", id));
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value(title))
        .andExpect(jsonPath("$.content").value(content));

    Mockito.verify(blogService, Mockito.times(1)).findBy(anyLong());
  }

  @Test
  void testUpdateArticle() throws Exception {
    String title2 = "mock_title2";
    String content2 = "mock_content2";

    UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(title2, content2);
    ObjectMapper objectMapper = new ObjectMapper();
    String updateRequestJson = objectMapper.writeValueAsString(updateArticleRequest);

    Mockito.when(blogService.update(anyLong(), any(UpdateArticleRequest.class)))
        .thenReturn(new Article(title2, content2));

    ResultActions resultActions2 = mockMvc.perform(
        put("/api/articles/1")
            .content(updateRequestJson)
            .contentType(MediaType.APPLICATION_JSON)
    );

    resultActions2.andExpect(status().isOk())
        .andExpect(jsonPath("title").value(updateArticleRequest.getTitle()))
        .andExpect(jsonPath("content").value(updateArticleRequest.getContent()));
  }
}