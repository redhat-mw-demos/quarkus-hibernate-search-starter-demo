package org.acme.product.dto;

import org.acme.product.model.Product;
import org.acme.product.model.ProductDepartment;
import org.acme.product.model.ProductVariant;
import org.hibernate.search.util.common.data.Range;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.Map;

@Mapper(componentModel = "cdi")
public interface ProductMapper {

    ProductOutputDto toDto(Product product);

    void fromDto(@MappingTarget Product product, ProductInputDto dto);

    ProductVariantOutputDto toDto(ProductVariant variant);

    void fromDto(@MappingTarget ProductVariant productVariant, ProductVariantInputDto dto);

    ProductDepartment fromDto(ProductDepartmentDto department);

    default PriceRangeDto fromDto(Range<BigDecimal> range) {
        for (PriceRangeDto dto : PriceRangeDto.values()) {
            if (range.equals(dto.value)) {
                return dto;
            }
        }
        throw new IllegalArgumentException("Not a supported price range: " + range);
    }

    Map<ProductDepartmentDto, Long> toDepartmentAggregationDto(Map<ProductDepartment, Long> aggregation);

    Map<PriceRangeDto, Long> toPriceRangeAggregationDto(Map<Range<BigDecimal>, Long> aggregation);

}
