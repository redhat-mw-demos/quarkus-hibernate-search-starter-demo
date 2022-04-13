package org.acme.product.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class ProductVariant extends PanacheEntity {

    @ManyToOne
    public Product product;

    public String name;
    @Column(precision = 8, scale = 2)
    public BigDecimal price;

}
