package com.tuyenngoc.bookstore.test;

import com.tuyenngoc.bookstore.constant.AgeGroup;
import com.tuyenngoc.bookstore.domain.entity.*;
import com.tuyenngoc.bookstore.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class DatasourceConfig {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BannerRepository bannerRepository;

    private final AuthorRepository authorRepository;

    private final BookSetRepository bookSetRepository;

    private final ProductImageRepository productImageRepository;

    @PostConstruct
    public void initData() {
        if (productRepository.count() == 0) {
            Category category = new Category();
            category.setId(1);
            category.setCreatedBy("");
            category.setLastModifiedBy("");
            category.setName("category 1");
            category.setImage("https://res.cloudinary.com/dkegqlchp/image/upload/v1708132837/nang-tien_8a34fbb897724c39a97db29cf9a39fa8_ml0exs.jpg");
            categoryRepository.save(category);

            BookSet bookSet = new BookSet();
            bookSet.setId(1);
            bookSet.setName("book set 1");
            bookSet.setCreatedBy("");
            bookSet.setLastModifiedBy("");
            bookSetRepository.save(bookSet);

            Author author = new Author();
            author.setId(1);
            author.setFullName("ok");
            author.setCreatedBy("");
            author.setLastModifiedBy("");
            authorRepository.save(author);

            productRepository.saveAll(IntStream.range(0, 200)
                    .mapToObj(i -> {
                        ProductImage productImage = new ProductImage();

                        Product product = new Product(i,
                                "product" + i,
                                "this is description" + i,
                                "https://res.cloudinary.com/dkegqlchp/image/upload/v1708132837/nang-tien_8a34fbb897724c39a97db29cf9a39fa8_ml0exs.jpg",
                                10000 * i,
                                10,
                                "isbn" + i,
                                "publisher" + i,
                                "12x12",
                                i + 10,
                                "cover type" + i,
                                10 * i,
                                i + 10,
                                i + 20,
                                Set.of(AgeGroup.CHILD),
                                List.of(productImage),
                                null,
                                null,
                                category,
                                bookSet,
                                Set.of(author),
                                null
                        );
                        product.setLastModifiedBy("tuyenngoc");

                        productImage.setUrl("https://res.cloudinary.com/dkegqlchp/image/upload/v1708132837/nang-tien_8a34fbb897724c39a97db29cf9a39fa8_ml0exs.jpg");
                        productImage.setProduct(product);

                        return product;
                    }).collect(Collectors.toList())
            );
        }
    }
}