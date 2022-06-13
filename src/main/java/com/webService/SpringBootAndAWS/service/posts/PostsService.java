package com.webService.SpringBootAndAWS.service.posts;

import com.webService.SpringBootAndAWS.domain.posts.PostsRepository;
import com.webService.SpringBootAndAWS.web.dto.PostsSaveRequestDto;
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
}
