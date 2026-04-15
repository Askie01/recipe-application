package com.askie01.recipeapplication.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PageResponse <T> {
    private List<T> items;
    private Integer page;
    private Integer size;
    private Long totalItems;
    private Integer totalPages;
    private boolean hasNext;

    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T> builder()
                .items(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .build();
    }
}
