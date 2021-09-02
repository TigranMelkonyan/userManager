package com.iguan.demo.usermanager.model.rest.response.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 01/09/2021
 * Time: 20:29
 */
public class PageResponse<T> {

    private final List<T> items = new ArrayList<>();
    private final Long totalCount;

    public PageResponse(List<T> items, Long totalCount) {
        this.items.addAll(items);
        this.totalCount = totalCount;
    }

    public static PageResponse from(final PageResponse model) {
        return new PageResponse(
                model.getItems(), model.getTotalCount()
        );
    }

    public List<T> getItems() {
        return items;
    }

    public Long getTotalCount() {
        return totalCount;
    }
}
