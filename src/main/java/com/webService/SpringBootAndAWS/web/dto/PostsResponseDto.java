package com.webService.SpringBootAndAWS.web.dto;

import com.webService.SpringBootAndAWS.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    // 해당 DTO 는 Entity 의 필드 중 일부만 사용하므로 굳이 모든 필드를 가진 생성자가 필요하지 않다.
    // 따라서 entity 의 값을 받아와서 사용한다.
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
