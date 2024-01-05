package com.bookstore.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String featuredImage;

    @Column(nullable = false)
    private double price;

    private int discount;

    private String isbn;// Mã số sách quốc tế (nếu có).

    private String publisher;// Nhà xuất bản của sách.

    private String language;// Ngôn ngữ của sách.

    private String format;// Định dạng của sách (ví dụ: bìa mềm, bìa cứng, ebook, audiobook, v.v.).

    private String dimensions;// Kích thước của sách (ví dụ: chiều cao, chiều rộng).

    private double weight;

    @Temporal(TemporalType.DATE)
    private LocalDate publicationDate;// Ngày xuất bản của sách.

    @Column(name = "cover_type")
    private String coverType;// Loại bìa của sách.

    @Column(name = "age_classification")
    private String ageClassification; // Phân loại theo độ tuổi

    @Column(name = "issuing_unit")
    private String issuingUnit;// Đơn vị phát hành của sách.

    @Column(name = "page_count")
    private Integer pageCount;// Số trang của sách.

    @Column(name = "stock_quantity")
    private Integer stockQuantity;// Số lượng sách còn trong kho.

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY"), referencedColumnName = "category_id")
    @JsonBackReference
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_author",
            joinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_ID"), referencedColumnName = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_AUTHOR_ID"), referencedColumnName = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteProducts", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Customer> favoriteByCustomers = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductImage> images;
}
