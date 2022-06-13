package com.webService.SpringBootAndAWS.service.posts;

import com.webService.SpringBootAndAWS.domain.posts.Posts;
import com.webService.SpringBootAndAWS.domain.posts.PostsRepository;
import com.webService.SpringBootAndAWS.web.dto.PostsResponseDto;
import com.webService.SpringBootAndAWS.web.dto.PostsSaveRequestDto;
import com.webService.SpringBootAndAWS.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {

        // 바인딩된 DTO 클래스를 Entity 로 변환한 뒤, ID 를 반환한다.
        // TODO : toEntity 는 Controller 단이 아닌, Service 단에 있어도 괜찮을까 ?
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    // 엔티티 값에 변동이 없는 조회 메서드는 Transactional 이 필요없다 ?
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }
}
