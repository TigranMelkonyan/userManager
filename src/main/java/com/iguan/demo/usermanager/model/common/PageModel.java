package com.iguan.demo.usermanager.model.common;

import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 01/09/2021
 * Time: 00:27
 */
public class PageModel<T> {

    private final List<T> items;
    private final Long totalCount;

    public PageModel(List<T> items, Long totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public Long getTotalCount() {
        return totalCount;
    }
}
