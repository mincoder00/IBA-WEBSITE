package com.iba.springbootdeveloper.dto;

import com.iba.springbootdeveloper.domain.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String category;
    private String author;

    @Builder
    public ArticleViewResponse(Article article){
        this.id= article.getId();
        this.title= article.getTitle();
        this.content= article.getContent();
        this.createdAt= article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.category= article.getCategory();
        this.author = article.getAuthor();
    }
}
