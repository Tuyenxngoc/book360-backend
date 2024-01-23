package com.tuyenngoc.bookstore.domain.entity.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class FlagUserDateAuditing extends UserDateAuditing {

    private Boolean deleteFlag;

    private Boolean activeFlag;

}
