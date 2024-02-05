package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.AgeGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgeGroupDto {

    private String label;

    private String key;

    public AgeGroupDto(AgeGroup ageGroup) {
        this.label = ageGroup.getLabel();
        this.key = ageGroup.name().toLowerCase();
    }

}
