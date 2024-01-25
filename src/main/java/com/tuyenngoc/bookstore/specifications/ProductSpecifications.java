package com.tuyenngoc.bookstore.specifications;

import com.tuyenngoc.bookstore.domain.entity.Category_;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.Product_;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> nameLike(String name) {
        return (root, query, builder) ->
                builder.like(root.get(Product_.name), "%" + name + "%");
    }

    public static Specification<Product> filterProducts(
            String name,
            int sellerStockMax,
            int sellerStockMin,
            int soldMax,
            int soldMin,
            int categoryId
    ) {
        return (root, query, builder) -> {
            query.distinct(true);

            Predicate predicate = builder.conjunction();
            if (StringUtils.isNotBlank(name)) {
                predicate = builder.and(predicate, nameLike(name).toPredicate(root, query, builder));
            }
            if (sellerStockMax > 0) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(Product_.stockQuantity), sellerStockMax));
            }
            if (sellerStockMin > 0) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Product_.stockQuantity), sellerStockMin));
            }
            if (soldMax > 0) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(Product_.soldQuantity), soldMax));
            }
            if (soldMin > 0) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Product_.soldQuantity), soldMin));
            }
            if (categoryId > 0) {
                predicate = builder.and(predicate, builder.equal(root.get(Product_.category).get(Category_.id), categoryId));
            }
            return predicate;
        };
    }
}
