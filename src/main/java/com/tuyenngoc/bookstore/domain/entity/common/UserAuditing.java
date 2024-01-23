package com.tuyenngoc.bookstore.domain.entity.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class UserAuditing {

    private String createdBy;

    private String lastModifiedBy;

}
