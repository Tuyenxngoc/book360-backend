package com.tuyenngoc.bookstore.test;

import com.tuyenngoc.bookstore.domain.entity.Banner;
import com.tuyenngoc.bookstore.domain.entity.Category;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.repository.BannerRepository;
import com.tuyenngoc.bookstore.repository.CategoryRepository;
import com.tuyenngoc.bookstore.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class DatasourceConfig {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BannerRepository bannerRepository;

    @PostConstruct
    public void initData() {

        if (bannerRepository.count() == 0) {
            bannerRepository.saveAll(IntStream.range(0, 20).mapToObj(i ->
                            new Banner(i + 1, "https://res.cloudinary.com/dkegqlchp/image/upload/v1705025283/ms_banner_img3_jeieut.webp", "/test", i))
                    .collect(Collectors.toList()));
        }

        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(IntStream.range(0, 3).mapToObj(i ->
                            new Category(i, "category" + i, "", null))
                    .collect(Collectors.toList()));
        }

        if (productRepository.count() == 0) {
            productRepository.saveAll(IntStream.range(0, 100)
                    .mapToObj(i ->
                            new Product(i,
                                    "product" + i,
                                    "this is description" + i,
                                    "https://product.hstatic.net/200000343865/product/4_2e7827a50f964419affcd78ff0614200_large.jpg",
                                    10000 * i,
                                    10,
                                    "isbn" + i,
                                    "publisher" + i,
                                    "lang" + i,
                                    "format" + i,
                                    "12x12",
                                    10 * i,
                                    LocalDate.now(),
                                    "cover" + i,
                                    "",
                                    "",
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                            ))
                    .collect(Collectors.toList())
            );
        }
    }
}
