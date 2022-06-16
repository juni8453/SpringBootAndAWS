package com.webService.SpringBootAndAWS.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Service 단에서 사용하는 수정 메서드이다.
    // DTO 에 바인딩 된 값들을 이 메서드를 통해 Entity 값에 집어넣는 형식이다.
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
