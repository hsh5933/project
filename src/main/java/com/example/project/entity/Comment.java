package com.example.project.entity;

import com.example.project.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //해당댓글 엔티티 여러개가, 하나의 Article에 연관
    @JoinColumn(name = "article_id") //테이블에서 연결된 대상의 이름지정
    private Article article; //댓글의 부모게시글

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        //예외 처리
        if(dto.getId() !=null){
            throw new IllegalArgumentException("댓글 생성실패! 댓글의 id가 있어야 합니다.");
        }
        if(dto.getArticleId() != article.getID()){
            throw new IllegalArgumentException("댓글 생성실패! 게시글의 id가 잘못되었습니다.");
        }

        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );


    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if(this.id != dto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        }

        // 객체를 갱신
        if(dto.getNickname()!=null){
            this.nickname = dto.getNickname();
        }
        if(dto.getBody() != null){
            this.body = dto.getBody();
        }

    }
}
