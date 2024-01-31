package com.tuyenngoc.bookstore.domain.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class FlagUserDateAuditing extends UserDateAuditing {

    @Column(nullable = false)
    public Boolean deleteFlag = Boolean.FALSE;

    @Column(nullable = false)
    private Boolean activeFlag = Boolean.TRUE;

}
