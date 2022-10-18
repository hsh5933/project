package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@AllArgsConstructor //생성자
@NoArgsConstructor //기본생성자
@Entity //Db가 해당 객체를 인식하게하는기능 (해당클래스로 테이블을 만든다)
public class Article {
    @Id //대표값 지정 like a 주민번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동으로 생성하는 어노테이션
    private Long id;

    @Column
    private String title;
    @Column
    private String content;


    public Long getID() {
        return id;
    }

    public void patch(Article article) {
        if(article.title !=null){
            this.title = article.title;
        }
        if(article.content !=null){
            this.content = article.content;
        }
    }
}
