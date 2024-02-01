package com.tuyenngoc.bookstore.specifications;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.domain.entity.Category_;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.Product_;
import com.tuyenngoc.bookstore.exception.InvalidException;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.name), "%" + name + "%");
    }

    public static Specification<Product> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get(Product_.deleteFlag));
    }

    private static Object castToRequiredType(Class<?> fieldType, String value) {
        try {
            if (fieldType.isAssignableFrom(Double.class)) {
                return Double.valueOf(value);
            } else if (fieldType.isAssignableFrom(Float.class)) {
                return Float.valueOf(value);
            } else if (fieldType.isAssignableFrom(Long.class)) {
                return Long.valueOf(value);
            } else if (fieldType.isAssignableFrom(Integer.class)) {
                return Integer.valueOf(value);
            } else if (fieldType.isAssignableFrom(Short.class)) {
                return Short.valueOf(value);
            } else if (fieldType.isAssignableFrom(Byte.class)) {
                return Byte.valueOf(value);
            }
        } catch (NumberFormatException e) {
            throw new InvalidException(ErrorMessage.INVALID_NUMBER_FORMAT);
        }
        return null;
    }

    public static Specification<Product> filterProducts(
            String keyword,
            String searchBy,
            int sellerStockMax,
            int sellerStockMin,
            int soldMax,
            int soldMin,
            int categoryId
    ) {
        return (root, query, builder) -> {
            query.distinct(true);

            Predicate predicate = builder.conjunction();
            if (StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(searchBy)) {
                switch (searchBy) {
                    case Product_.NAME -> predicate = builder.and(predicate, builder.like(root.get(Product_.NAME), "%" + keyword + "%"));

                    case Product_.PRICE -> predicate = builder.and(predicate, builder.equal(root.get(Product_.PRICE),
                            castToRequiredType(root.get(Product_.PRICE).getJavaType(), keyword)));

                    case Product_.DISCOUNT -> predicate = builder.and(predicate, builder.equal(root.get(Product_.DISCOUNT),
                            castToRequiredType(root.get(Product_.DISCOUNT).getJavaType(), keyword)));

                    case Product_.ISBN -> predicate = builder.and(predicate, builder.like(root.get(Product_.ISBN), "%" + keyword + "%"));

                    case Product_.PUBLISHER -> predicate = builder.and(predicate, builder.like(root.get(Product_.PUBLISHER), "%" + keyword + "%"));

                    case Product_.SIZE -> predicate = builder.and(predicate, builder.like(root.get(Product_.SIZE), "%" + keyword + "%"));
                }
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
