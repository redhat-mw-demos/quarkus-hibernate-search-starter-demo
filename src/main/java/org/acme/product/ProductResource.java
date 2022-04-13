package org.acme.product;

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

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.smallrye.common.annotation.Blocking;
import org.acme.product.dto.ProductInputDto;
import org.acme.product.dto.ProductMapper;
import org.acme.product.dto.ProductOutputDto;
import org.acme.product.dto.ProductVariantInputDto;
import org.acme.product.dto.ProductVariantOutputDto;
import org.acme.product.dto.SearchInputDto;
import org.acme.product.dto.SearchOutputDto;
import org.acme.product.model.Product;
import org.acme.product.model.ProductVariant;

import java.util.HashMap;
import java.util.Locale;
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
        StringBuilder where = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        if (input.text != null && !input.text.isBlank()) {
            if (where.length() == 0) {
                where.append(" where ");
            }
            where.append("(");
            String[] words = input.text.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String paramName = "text" + i;
                if (i > 0) {
                    where.append(" and ");
                }
                where.append(String.format(
                        "(lower(p.name) like :%1$s or lower(p.description) like :%1$s or lower(v.name) like :%1$s)",
                        paramName));
                parameters.put(paramName, "%" + words[i].trim().toLowerCase(Locale.ROOT) + "%");
            }
            where.append(")");
        }
        if (input.department != null) {
            if (where.length() == 0) {
                where.append(" where ");
            } else {
                where.append(" and ");
            }
            where.append("department = :department");
            parameters.put("department", mapper.fromDto(input.department).name());
        }
        PanacheQuery<Product> query = Product.find(
                        "select distinct p from Product p left join p.variants v" + where,
                        Sort.by("p.name"),
                        parameters
                )
                .page(input.page, SEARCH_PAGE_SIZE);
        return new SearchOutputDto<>(
                query.list().stream().map(mapper::toDto).collect(Collectors.toList()),
                query.count()
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
