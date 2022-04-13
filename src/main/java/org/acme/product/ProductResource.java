package org.acme.product;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import io.smallrye.common.annotation.Blocking;
import org.acme.product.dto.PriceRangeDto;
import org.acme.product.dto.ProductInputDto;
import org.acme.product.dto.ProductMapper;
import org.acme.product.dto.ProductOutputDto;
import org.acme.product.dto.ProductVariantInputDto;
import org.acme.product.dto.ProductVariantOutputDto;
import org.acme.product.dto.SearchInputDto;
import org.acme.product.dto.SearchOutputDto;
import org.acme.product.model.Product;
import org.acme.product.model.ProductDepartment;
import org.acme.product.model.ProductVariant;
import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.engine.search.common.BooleanOperator;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.mapping.SearchMapping;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.util.common.data.Range;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/product")
@Blocking
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class ProductResource {

    private static final int SEARCH_PAGE_SIZE = 20;

    @Inject
    ProductMapper mapper;

    @Inject
    SearchMapping searchMapping;

    @Inject
    SearchSession searchSession;

    @Transactional(Transactional.TxType.NEVER)
    void reindexOnStart(@Observes StartupEvent event) throws InterruptedException {
        if ("dev".equals(ProfileManager.getActiveProfile())) {
            searchMapping
                    .scope(Product.class)
                    .massIndexer()
                    .startAndWait();
        }
    }

    @POST
    @Path("/")
    public ProductOutputDto create(ProductInputDto input) {
        Product product = new Product();
        mapper.fromDto(product, input);
        product.persist();
        return mapper.toDto(product);
    }

    @GET
    @Path("/{id}")
    public ProductOutputDto retrieve(@PathParam("id") long id) {
        return mapper.toDto(find(id));
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") long id, ProductInputDto input) {
        mapper.fromDto(find(id), input);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        find(id).delete();
    }

    private Product find(long id) {
        Product found = Product.findById(id);
        if (found == null) {
            throw new NotFoundException();
        }
        return found;
    }

    @POST
    @Path("/search")
    public SearchOutputDto<ProductOutputDto> search(SearchInputDto input) {
        AggregationKey<Map<ProductDepartment, Long>> countByDepartment =
                AggregationKey.of("count-by-department");
        AggregationKey<Map<Range<BigDecimal>, Long>> countByPriceRange =
                AggregationKey.of("count-by-price-range");
        SearchResult<Product> result = searchSession.search(Product.class)
                .where(f -> f.bool(b -> {
                    b.must(f.matchAll()); // Match all by default
                    if (input.text != null && !input.text.isBlank()) {
                        b.must(f.simpleQueryString()
                                .fields("name", "description", "variants.name")
                                .matching(input.text)
                                .defaultOperator(BooleanOperator.AND));
                    }
                    if (input.department != null) {
                        b.must(f.match().field("department")
                                .matching(mapper.fromDto(input.department)));
                    }
                    if (input.priceRange != null) {
                        b.must(f.range().field("variants.price")
                                .range(input.priceRange.value));
                    }
                }))
                .sort(f -> f.field("name_keyword"))
                .aggregation(countByDepartment, f -> f.terms()
                        .field("department", ProductDepartment.class)
                        .orderByTermAscending()
                        .minDocumentCount(0))
                .aggregation(countByPriceRange, f -> f.range()
                        .field("variants.price", BigDecimal.class)
                        .ranges(EnumSet.allOf(PriceRangeDto.class)
                                .stream().map(r -> r.value)
                                .collect(Collectors.toList())))
                .fetch(input.page * SEARCH_PAGE_SIZE, SEARCH_PAGE_SIZE);
        return new SearchOutputDto<>(
                result.hits().stream().map(mapper::toDto).collect(Collectors.toList()),
                result.total().hitCount(),
                mapper.toDepartmentAggregationDto(result.aggregation(countByDepartment)),
                mapper.toPriceRangeAggregationDto(result.aggregation(countByPriceRange))
        );
    }

    @POST
    @Path("/{id}/variant")
    public ProductVariantOutputDto createVariant(@PathParam("id") long productId, ProductVariantInputDto input) {
        Product product = find(productId);
        ProductVariant variant = new ProductVariant();
        variant.product = product;
        product.variants.add(variant);
        mapper.fromDto(variant, input);
        variant.persist();
        return mapper.toDto(variant);
    }

    @GET
    @Path("/variant/{id}")
    public ProductVariantOutputDto retrieveVariant(@PathParam("id") long id) {
        return mapper.toDto(findVariant(id));
    }

    @PUT
    @Path("/variant/{id}")
    public void updateVariant(@PathParam("id") long id, ProductVariantInputDto input) {
        mapper.fromDto(findVariant(id), input);
    }

    @DELETE
    @Path("/variant/{id}")
    public void deleteVariant(@PathParam("id") long id) {
        ProductVariant variant = findVariant(id);
        variant.product.variants.remove(variant);
    }

    private ProductVariant findVariant(long id) {
        ProductVariant found = ProductVariant.findById(id);
        if (found == null) {
            throw new NotFoundException();
        }
        return found;
    }
}
