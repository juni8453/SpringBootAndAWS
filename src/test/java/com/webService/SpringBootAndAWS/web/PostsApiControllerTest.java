package com.webService.SpringBootAndAWS.web;

import com.webService.SpringBootAndAWS.domain.posts.Posts;
import com.webService.SpringBootAndAWS.domain.posts.PostsRepository;
import com.webService.SpringBootAndAWS.web.dto.PostsSaveRequestDto;
import com.webService.SpringBootAndAWS.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 아래처럼 랜덤 포트 설정을 해줘야 restTemplate 가 동작한다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    /* 왜 HelloController 와 달리 @WebMvcTest 를 사용하지 않았는가 ?
     *  @WebMvcTest 의 경우, JPA 기능이 작동하지 않기 때문 !
     *  아래 처럼 JPA 기능을 한꺼번에 테스트하기 위해선, @SpringBootTest, TestRestTemplate 를 사용하면 된다.
     * */
    @Test
    @DisplayName("게시글을 등록하는 테스트입니다.")
    void save() throws Exception {

        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("tany@naver.com")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("게시글을 수정하는 테스트입니다")
    void update() {

        // given
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("tany@naver.com")
                .build());

        Long postsId = savePosts.getId();
        String updateTitle = "title2";
        String updateContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/"+ postsId;

        // TODO : 요청 또는 응답에 해당하는 HttpHeader, HttpBody 를 포함하는 클래스
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        // TODO : PUT 메서드는 따로 restTemplate 에서 메서드를 제공하지 않으므로 exchange() 사용
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);
    }
}