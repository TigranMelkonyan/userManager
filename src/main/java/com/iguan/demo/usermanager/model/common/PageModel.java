package com.iguan.demo.usermanager.model.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 01/09/2021
 * Time: 00:27
 */
public class PageModel<T> {

    private final List<T> items = new ArrayList<>();
    private final long totalCount;

    public PageModel(List<T> items, long totalCount) {
        this.items.addAll(items);
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public long getTotalCount() {
        return totalCount;
    }
}
