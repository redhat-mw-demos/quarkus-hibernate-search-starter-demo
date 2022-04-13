package org.acme.product.dto;

import java.util.List;

public class SearchOutputDto<T> {

    public final List<T> hits;
    public final long totalHitCount;

    public SearchOutputDto(List<T> hits, long totalHitCount) {
        this.hits = hits;
        this.totalHitCount = totalHitCount;
    }
}
