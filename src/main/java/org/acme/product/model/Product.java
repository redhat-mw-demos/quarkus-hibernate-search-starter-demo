package org.acme.product.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
@Indexed
public class Product extends PanacheEntity {

    @FullTextField
    @KeywordField(name = "name_keyword", sortable = Sortable.YES)
    public String name;
    @FullTextField
    public String description;
    @Enumerated(EnumType.STRING)
    @KeywordField(aggregable = Aggregable.YES)
    public ProductDepartment department;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    @IndexedEmbedded
    public List<ProductVariant> variants = new ArrayList<>();

}
