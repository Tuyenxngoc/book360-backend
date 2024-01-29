package com.tuyenngoc.bookstore.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AgeGroup {

    PRESCHOOL("Nhà trẻ, Mẫu giáo (0-5 tuổi)"),
    CHILD("Nhi đồng (6-10 tuổi)"),
    ADOLESCENT("Thiếu niên (11-15 tuổi)"),
    YOUNG_ADULT("Tuổi mới lớn (16-18 tuổi)"),
    ADULT("Tuổi trưởng thành (Trên 18 tuổi)");

    private final String label;
}
