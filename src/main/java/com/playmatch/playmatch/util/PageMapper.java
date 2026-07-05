package com.playmatch.playmatch.util;
import org.springframework.data.domain.Page;
import com.playmatch.playmatch.dto.PagedResponse;

public class PageMapper {

    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}