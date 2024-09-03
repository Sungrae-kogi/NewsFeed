package com.sparta.newsfeed.post;

import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import java.util.Arrays;
import lombok.Getter;
import org.springframework.data.domain.Sort.Direction;

@Getter
public enum SortCriteria {

    RECENT_CREATED_DATE("recentCreatedDate", "createdAt", Direction.DESC),
    LAST_MODIFIED_DATE("lastModified", "modifiedAt", Direction.DESC),
    MOST_LIKED("mostLiked", "like", Direction.ASC);

    private final String name;
    private final String sort;
    private final Direction direction;

    SortCriteria(final String name, final String sort, final Direction direction) {
        this.name = name;
        this.sort = sort;
        this.direction = direction;
    }

    public static SortCriteria findBySort(final String sort) {
        return Arrays.stream(SortCriteria.values())
                .filter(criteria -> criteria.sort.equals(sort))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNSUPPORTED_SORT_CRITERIA));
    }
}
