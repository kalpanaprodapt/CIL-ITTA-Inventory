package com.uniti.cil.itta.inventory.common.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public class PageResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static <T> PageResponse<T> from(Page<T> source) {
        PageResponse<T> r = new PageResponse<>();
        r.content = source.getContent();
        r.page = source.getNumber();
        r.size = source.getSize();
        r.totalElements = source.getTotalElements();
        r.totalPages = source.getTotalPages();
        return r;
    }

    public static <T> PageResponse<T> of(List<T> content, int page, int size, long total) {
        PageResponse<T> r = new PageResponse<>();
        r.content = content;
        r.page = page;
        r.size = size;
        r.totalElements = total;
        r.totalPages = size == 0 ? 0 : (int) Math.ceil((double) total / size);
        return r;
    }

    public List<T> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
}
