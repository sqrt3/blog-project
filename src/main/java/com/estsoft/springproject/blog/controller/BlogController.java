package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.ArticleResponse;
import com.estsoft.springproject.blog.domain.dto.ArticleWithCommentsResponse;
import com.estsoft.springproject.blog.domain.dto.UpdateArticleRequest;
import com.estsoft.springproject.blog.service.BlogCommentService;
import com.estsoft.springproject.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "블로그 게시물 저장/수정/삭제용 API", description = "블로그의 게시물 CRUD 관련 API 입니다.")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService, BlogCommentService blogCommentService) {
        this.blogService = blogService;
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> writeArticle(@RequestBody AddArticleRequest request) {
        Article article = blogService.saveArticle(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(request.toEntity().toEntity());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "100", description = "요청에 성공했습니다.", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "블로그 전체 목록 보기", description = "블로그 메인 화면에서 보여주는 전체 목록")
    @GetMapping("/articles") // get List
    public ResponseEntity<List<ArticleResponse>> findAll() {
        List<ArticleResponse> articleList = blogService.findAll()
                .stream()
                .map(Article::toEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleList);
    }

    @Parameter(name = "id", description = "블로그 글 ID", example = "45")
    @GetMapping("/articles/{id}") // get Specific id's article
    public ResponseEntity<ArticleResponse> findById(@PathVariable Long id) {
        Article article = blogService.findBy(id);
        if (article != null)
            return ResponseEntity.ok(article.toEntity());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Parameter(name = "id", description = "블로그 글 ID", example = "45")
    @DeleteMapping("/articles/{id}") // delete article
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Article article = blogService.findBy(id);
        if (article != null) {
            blogService.deleteBy(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Parameter(name = "id", description = "블로그 글 ID", example = "45")
    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateById(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article article = blogService.update(id, request);
        return ResponseEntity.ok().body(article.toEntity());
    }

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<ArticleWithCommentsResponse> getArticleWithComments(@PathVariable Long articleId) {
        ArticleWithCommentsResponse response = blogService.findArticleWithComments(articleId);
        return ResponseEntity.ok(response);
    }
}
