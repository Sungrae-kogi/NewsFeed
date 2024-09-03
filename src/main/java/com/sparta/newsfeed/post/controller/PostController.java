package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.SortCriteria;
import com.sparta.newsfeed.post.dto.request.PostCreateRequestDto;
import com.sparta.newsfeed.post.dto.request.PostEditRequestDto;
import com.sparta.newsfeed.post.dto.response.PostResponseDto;
import com.sparta.newsfeed.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDate;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public void createPost(@Valid @RequestBody final PostCreateRequestDto postCreateRequestDto,
            final HttpServletRequest httpServletRequest
    ) {
        long userId = getUserIdFromServletRequestOrThrow(httpServletRequest);
        postService.createPost(userId, postCreateRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable final long postId) {
        return postService.getPost(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/posts/{postId}")
    public void editPost(@PathVariable final long postId,
            @Valid @RequestBody final PostEditRequestDto postEditRequestDto) {
        postService.editPost(postId, postEditRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable final long postId) {
        postService.deletePost(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(
            @RequestParam(defaultValue = "0") final int pageNumber,
            @RequestParam(defaultValue = "recentCreatedDate") final String sort,
            @RequestParam(defaultValue = "2000-01-01") final String startDate,
            @RequestParam String endDate
    ) {
        if (endDate == null) {
            endDate = LocalDate.now().toString();
        }
        SortCriteria sortCriteria = SortCriteria.findBySort(sort);
        PageRequest pageRequest = PageRequest.of(
                pageNumber,
                DEFAULT_PAGE_SIZE,
                sortCriteria.getDirection(),
                sortCriteria.getSort()
        );

        return postService.getPosts(pageRequest, startDate, endDate);
    }

    private long getUserIdFromServletRequestOrThrow(final HttpServletRequest httpServletRequest) {
        Object userIdAttribute = httpServletRequest.getAttribute("userId");
        if (userIdAttribute instanceof Long) {
            return (long) userIdAttribute;
        }
        throw new ApplicationException(ErrorCode.BAD_REQUEST);
    }
}
