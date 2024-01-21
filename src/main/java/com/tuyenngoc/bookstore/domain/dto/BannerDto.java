package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.domain.entity.Banner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerDto {

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private int id;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String image;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String url;

    @Positive(message = ErrorMessage.INVALID_VIEW_ORDER)
    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private int viewOrder;

    public BannerDto(Banner banner) {
        this.id = banner.getId();
        this.image = banner.getImage();
        this.url = banner.getUrl();
        this.viewOrder = banner.getViewOrder();
    }
}
