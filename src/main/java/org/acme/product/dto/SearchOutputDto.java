package org.acme.product.dto;

import java.util.List;
import java.util.Map;

public class SearchOutputDto<T> {

    public final List<T> hits;
    public final long totalHitCount;
	public final Map<ProductDepartmentDto, Long> countByDepartment;
    public final Map<PriceRangeDto, Long> countByPriceRange;

    public SearchOutputDto(List<T> hits, long totalHitCount,
                           Map<ProductDepartmentDto, Long> countByDepartment,
                           Map<PriceRangeDto, Long> countByPriceRange) {
        this.hits = hits;
        this.totalHitCount = totalHitCount;
        this.countByDepartment = countByDepartment;
        this.countByPriceRange = countByPriceRange;
    }
}
