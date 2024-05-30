package com.tuyenngoc.bookstore.specifications;

import com.tuyenngoc.bookstore.domain.entity.Category_;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.Product_;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import static com.tuyenngoc.bookstore.util.SpecificationsUtil.castToRequiredType;

public class ProductSpecifications {

    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.name), "%" + name + "%");
    }

    public static Specification<Product> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get(Product_.deleteFlag));
    }

    public static Specification<Product> filterProducts(
            String keyword,
            String searchBy,
            Integer sellerStockMax,
            Integer sellerStockMin,
            Integer soldMax,
            Integer soldMin,
            Integer categoryId
    ) {
        return (root, query, builder) -> {
            query.distinct(true);

            Predicate predicate = builder.conjunction();

            if (StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(searchBy)) {
                switch (searchBy) {
                    case Product_.NAME -> predicate = builder.and(predicate, builder.like(root.get(Product_.name), "%" + keyword + "%"));

                    case Product_.PRICE -> predicate = builder.and(predicate, builder.equal(root.get(Product_.price),
                            castToRequiredType(root.get(Product_.price).getJavaType(), keyword)));

                    case Product_.DISCOUNT -> predicate = builder.and(predicate, builder.equal(root.get(Product_.discount),
                            castToRequiredType(root.get(Product_.discount).getJavaType(), keyword)));

                    case Product_.ISBN -> predicate = builder.and(predicate, builder.like(root.get(Product_.isbn), "%" + keyword + "%"));

                    case Product_.PUBLISHER -> predicate = builder.and(predicate, builder.like(root.get(Product_.publisher), "%" + keyword + "%"));

                    case Product_.SIZE -> predicate = builder.and(predicate, builder.like(root.get(Product_.size), "%" + keyword + "%"));
                }
            }
            if (sellerStockMax != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(Product_.stockQuantity), sellerStockMax));
            }
            if (sellerStockMin != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Product_.stockQuantity), sellerStockMin));
            }
            if (soldMax != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(Product_.soldQuantity), soldMax));
            }
            if (soldMin != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Product_.soldQuantity), soldMin));
            }
            if (categoryId != null) {
                predicate = builder.and(predicate, builder.equal(root.get(Product_.category).get(Category_.id), categoryId));
            }
            return predicate;
        };
    }
}
