package com.tuyenngoc.bookstore.test;

import com.tuyenngoc.bookstore.domain.entity.*;
import com.tuyenngoc.bookstore.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private final ProductImageRepository productImageRepository;

    @PostConstruct
    public void initData() {

        if (bannerRepository.count() == 0) {
            bannerRepository.saveAll(IntStream.range(0, 20).mapToObj(i ->
                            new Banner(i + 1, "https://res.cloudinary.com/dkegqlchp/image/upload/v1705742296/ms_banner_img4_ceuqne.webp", "/test", i))
                    .collect(Collectors.toList()));
        }

        final List<Category> categories = new ArrayList<>();
        if (categoryRepository.count() == 0) {
            categories.addAll(categoryRepository.saveAll(IntStream.range(0, 20).mapToObj(i ->
                            new Category(i, "category" + i, "https://res.cloudinary.com/dkegqlchp/image/upload/v1705837691/banner_home_pro_4_fs4kao.webp", null))
                    .collect(Collectors.toList())));
        }

        final List<Author> authors = new ArrayList<>();
        if (authorRepository.count() == 0) {
            authors.addAll(authorRepository.saveAll(IntStream.range(0, 10).mapToObj(i ->
                            new Author(i, "author" + i, null))
                    .collect(Collectors.toList())));
        }

        final List<ProductImage> productImages = new ArrayList<>();
        if (productImageRepository.count() == 0) {
            productImages.addAll(productImageRepository.saveAll(IntStream.range(0, 10).mapToObj(i ->
                            new ProductImage(i, "https://product.hstatic.net/200000343865/product/5_8d9416ec8a8740f282abd22dd1f8052a.jpg", null))
                    .collect(Collectors.toList())));
        }

        if (productRepository.count() == 0) {
            productRepository.saveAll(IntStream.range(0, 100)
                    .mapToObj(i ->
                            new Product(i,
                                    "product" + i,
                                    "this is description" + i,
                                    "https://product.hstatic.net/200000343865/product/5_8d9416ec8a8740f282abd22dd1f8052a.jpg",
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
                                    i + 1,
                                    i + 1,
                                    List.of(productImages.get((int) (Math.random() * productImages.size()))),
                                    null,
                                    null,
                                    categories.get((int) (Math.random() * categories.size())),
                                    Set.of(authors.get((int) (Math.random() * authors.size()))),
                                    null
                            ))
                    .collect(Collectors.toList())
            );
        }
    }
}
