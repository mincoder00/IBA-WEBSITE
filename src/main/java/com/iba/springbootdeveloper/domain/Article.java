package com.iba.springbootdeveloper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull(message = "제목은 필수 항목입니다.")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "내용은 필수 항목입니다.")
    @Column(name = "content", nullable = false)
    @Size(max = 1000, message = "내용은 최대 1000자까지 가능합니다.")
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull(message = "카테고리는 필수 항목입니다.")
    @Column(name = "category", nullable = false)
    private String category;

    @Builder
    public Article(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void update(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
