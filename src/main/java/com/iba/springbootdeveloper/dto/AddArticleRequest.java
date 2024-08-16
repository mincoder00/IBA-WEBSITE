package com.iba.springbootdeveloper.dto;

import com.iba.springbootdeveloper.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;
    private String category;

    public Article toEntity(String author) {
        return Article.builder()
                .title(title)
                .content(content)
                .category(category)
                .author(author)
                .build();
    }
}