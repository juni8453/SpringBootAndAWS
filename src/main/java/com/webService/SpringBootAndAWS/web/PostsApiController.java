package com.webService.SpringBootAndAWS.web;

import com.webService.SpringBootAndAWS.service.posts.PostsService;
import com.webService.SpringBootAndAWS.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    /*
        게시글을 저장하고 해당 게시글 번호를 반환하는 API
        Entity 에 값을 직접 바인딩하는게 아닌, DTO 를 바인딩한다.
    * */
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {

        return postsService.save(requestDto);
    }
}