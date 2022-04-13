package org.acme.product.dto;

import org.acme.product.model.Product;
import org.acme.product.model.ProductDepartment;
import org.acme.product.model.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface ProductMapper {

    ProductOutputDto toDto(Product product);

    void fromDto(@MappingTarget Product product, ProductInputDto dto);

    ProductVariantOutputDto toDto(ProductVariant variant);

    void fromDto(@MappingTarget ProductVariant productVariant, ProductVariantInputDto dto);

    ProductDepartment fromDto(ProductDepartmentDto department);

}
