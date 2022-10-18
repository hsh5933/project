package com.example.project.dto;

import com.example.project.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

//form데이터를 받아올곳!!
@AllArgsConstructor // 생성자
@ToString // tostring
public class ArticleForm {
    private Long id;
    private String title;
    private String content;


    public Article toEntity() {
        return new Article(id,title, content);
    }
}
