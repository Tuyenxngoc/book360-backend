package com.tuyenngoc.bookstore.domain.entity.common;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class UserDateAuditing extends DateAuditing {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "lastModified_by")
    private String lastModifiedBy;

}
