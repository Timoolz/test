package com.activedgetechnologies.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int pageNum;
    private int pageSize;
    private int numberOfElements;
    public PageResponse (Page<T> hibernatePage) {

        content = hibernatePage.getContent();
        totalPages = hibernatePage.getTotalPages();
        totalElements = hibernatePage.getTotalElements();
        pageNum = hibernatePage.getNumber();
        numberOfElements = hibernatePage.getNumberOfElements();
        pageSize = hibernatePage.getSize();
    }

}
