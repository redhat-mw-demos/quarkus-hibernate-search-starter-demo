package org.acme.product.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends PanacheEntity {

    public String name;
    public String description;
    @Enumerated(EnumType.STRING)
    public ProductDepartment department;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    public List<ProductVariant> variants = new ArrayList<>();

}
