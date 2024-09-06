package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.post.common.SortCriteria;
import com.sparta.newsfeed.post.dto.request.PostCreateRequestDto;
import com.sparta.newsfeed.post.dto.request.PostEditRequestDto;
import com.sparta.newsfeed.post.dto.response.PostResponseDto;
import com.sparta.newsfeed.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    public static final int DEFAULT_PAGE_SIZE = 10;

    private final PostService postService;
    private final JwtUtil jwtUtil;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public String createPost(
            @Valid @RequestBody final PostCreateRequestDto postCreateRequestDto,
            final HttpServletRequest httpServletRequest
    ) {
        long userId = getUserIdFromServletRequestOrThrow(httpServletRequest);
        postService.createPost(userId, postCreateRequestDto);
        return "게시글이 등록되었습니다";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable final long postId) {
        return postService.getPost(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/posts/{postId}")
    public String editPost(
        @PathVariable final long postId,
        @Valid @RequestBody final PostEditRequestDto postEditRequestDto,
        final HttpServletRequest httpServletRequest
    ) {
        String jwt = jwtUtil.getJwtFromHeader(httpServletRequest);
        Long userId = jwtUtil.getUserIdFromToken(jwt);
        postService.editPost(postId, userId, postEditRequestDto);
        return "게시물이 수정되었습니다";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable final long postId, final HttpServletRequest httpServletRequest) {
        String jwt = jwtUtil.getJwtFromHeader(httpServletRequest);
        Long userId = jwtUtil.getUserIdFromToken(jwt);
        postService.deletePost(postId, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(
            @RequestParam(defaultValue = "0") final int pageNumber,
            @RequestParam(defaultValue = "recentCreatedDate") final String sort,
            @RequestParam(defaultValue = "2000-01-01") final String startDate,
            @RequestParam(defaultValue = "2100-01-01") String endDate,
            final HttpServletRequest httpServletRequest
    ) {
        Long userId = getUserIdFromServletRequestOrThrow(httpServletRequest);

        LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        SortCriteria sortCriteria = SortCriteria.findBySort(sort);
        if (sortCriteria.isDateCriteria()) {
            PageRequest pageRequest = PageRequest.of(
                    pageNumber,
                    DEFAULT_PAGE_SIZE,
                    sortCriteria.getDirection(),
                    sortCriteria.getSort()
            );

            return postService.getNewsfeedOrderByDateDesc(
                    userId,
                    pageRequest,
                    startLocalDate.atStartOfDay(),
                    endLocalDate.atStartOfDay());
        }

        return postService.getNewsfeedOrderByLikeCountDesc(
                userId,
                pageNumber,
                startLocalDate.atStartOfDay(),
                endLocalDate.atStartOfDay());
    }

    private long getUserIdFromServletRequestOrThrow(final HttpServletRequest httpServletRequest) {
        return jwtUtil.getUserIdFromToken(jwtUtil.getJwtFromHeader(httpServletRequest));
    }
}
