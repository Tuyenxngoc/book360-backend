package com.tuyenngoc.bookstore.domain.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LastModifiedByDto {

    private String id;

    private String username;

    private String avatar;

}
