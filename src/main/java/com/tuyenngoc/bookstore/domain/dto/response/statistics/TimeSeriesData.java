package com.tuyenngoc.bookstore.domain.dto.response.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSeriesData {

    private long timestamp;

    private int value;

}