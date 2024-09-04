package com.sparta.newsfeed.post.common;

import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import java.util.Arrays;
import lombok.Getter;
import org.springframework.data.domain.Sort.Direction;

@Getter
public enum SortCriteria {

    RECENT_CREATED_DATE("recentCreatedDate", "createdAt", Direction.DESC, true),
    LAST_MODIFIED_DATE("lastModified", "modifiedAt", Direction.DESC, true),
    MOST_LIKED("mostLiked", "like", Direction.DESC, false);

    private final String name;
    private final String sort;
    private final Direction direction;
    private final boolean isDateCriteria;

    SortCriteria(final String name, final String sort, final Direction direction, final boolean isDateCriteria) {
        this.name = name;
        this.sort = sort;
        this.direction = direction;
        this.isDateCriteria = isDateCriteria;
    }

    public static SortCriteria findBySort(final String sort) {
        return Arrays.stream(SortCriteria.values())
                .filter(criteria -> criteria.name.equals(sort))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNSUPPORTED_SORT_CRITERIA));
    }
}
