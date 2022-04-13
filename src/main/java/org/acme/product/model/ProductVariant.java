package org.acme.product.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

@Entity
public class ProductVariant extends PanacheEntity {

    @ManyToOne
    public Product product;

    @FullTextField
    public String name;
    @Column(precision = 8, scale = 2)
    @GenericField(aggregable = Aggregable.YES)
    public BigDecimal price;

}
