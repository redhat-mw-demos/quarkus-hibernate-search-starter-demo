package org.acme.product.dto;

import java.util.List;

public class ProductOutputDto {
    public long id;
    public String name;
    public String description;
    public ProductDepartmentDto department;
    public List<ProductVariantOutputDto> variants;
}
