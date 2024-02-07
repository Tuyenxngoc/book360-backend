package com.tuyenngoc.bookstore.specifications;

import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.domain.entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import static com.tuyenngoc.bookstore.util.SpecificationsUtil.castToRequiredType;

public class BillSpecifications {

    public static Specification<Bill> filterBills(
            BillStatus billStatus,
            String keyword,
            String searchBy
    ) {
        return (root, query, builder) -> {
            query.distinct(true);

            Predicate predicate = builder.conjunction();

            if (StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(searchBy)) {
                switch (searchBy) {
                    case Bill_.ID -> predicate = builder.and(predicate, builder.equal(root.get(Bill_.id),
                            castToRequiredType(root.get(Bill_.id).getJavaType(), keyword)));

                    case Bill_.CONSIGNEE_NAME -> predicate = builder.and(predicate, builder.equal(root.get(Bill_.consigneeName), keyword));

                    case Product_.NAME -> {
                        Join<Bill, BillDetail> billDetailJoin = root.join(Bill_.billDetails);
                        Join<BillDetail, Product> productJoin = billDetailJoin.join(BillDetail_.product);
                        predicate = builder.and(predicate, builder.like(productJoin.get(Product_.name), "%" + keyword + "%"));
                    }
                }
            }

            if (billStatus != null) {
                predicate = builder.and(predicate, builder.equal(root.get(Bill_.billStatus), billStatus));
            }
            return predicate;
        };
    }
}
