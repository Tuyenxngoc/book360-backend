package com.tuyenngoc.bookstore.domain.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class FlagUserDateAuditing extends UserDateAuditing {

    @Column(name = "delete_flag")
    private Boolean deleteFlag = false;

}