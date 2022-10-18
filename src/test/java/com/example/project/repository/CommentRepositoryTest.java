package com.example.project.repository;

import com.example.project.entity.Article;
import com.example.project.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // JPA와 연동한 테스트
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;

    @Test

    @DisplayName("특정 게시글의 모든 댓글조회") // 테스트이름
    void findByArticleId() {
        // Case1 : 4번 게시글의 모든 댓글 조회
        {
            //입력 데이터 준비
            Long articleId = 4L;

            //실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            //예상하기
            Article article = new Article(4L,"당신의 인생 영화는?","댓글 ㄱ");
            Comment a = new Comment(1L, article, "Park","굿윌헌팅");
            Comment b = new Comment(2L, article, "Kim","아이앰셈");
            Comment c = new Comment(3L, article, "Choi","쇼생크탈출");
            List<Comment> expected = Arrays.asList(a,b,c);

            //검증
            assertEquals(expected.toString(),comments.toString(),"4번 글의 모든 댓글을 출력");
        }
    }

    @Test
    void findByNickname() {
        // Case1 : 닉네임 Park 댓글 조회
        {
            //입력 데이터 준비
            String nickname="Park";

            //실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            //예상하기
            Comment a = new Comment(1L,new Article(4L, "당신의 인생 영화는?","댓글 ㄱ"),"Park","굿윌헌팅");
            Comment b = new Comment(4L,new Article(5L, "당신의 소울 푸드는?","댓글 ㄱㄱ"),"Park","치킨");
            Comment c = new Comment(7L,new Article(6L, "당신의 취미는?","댓글 ㄱㄱㄱ"),"Park","축구");
            List<Comment> expected = Arrays.asList(a,b,c);

            //검증
            assertEquals(expected.toString(),comments.toString(),"park의 댓글 출력");
        }
    }
}