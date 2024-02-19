package com.tuyenngoc.bookstore.domain.dto.response.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesStatistics {

    private double sales; // Doanh số

    private int uv; // Số lượng người dùng duy nhất (Unique Visitors)

    private int pv; // Số lần truy cập trang (Page Views)

    private int orders; // Số lượng đơn hàng

    private int products; // Số lượng sản phẩm

    private double conversionRate; // Tỷ lệ chuyển đổi (Conversion Rate)

    private double salesPctDiff; // Phần trăm thay đổi của daonh số so với dữ liệu trước đó

    private double uvPctDiff; // Phần trăm thay đổi của số lượng người dùng duy nhất so với dữ liệu trước đó

    private double pvPctDiff; // Phần trăm thay đổi của số lần truy cập trang so với dữ liệu trước đó

    private double ordersPctDiff; // Phần trăm thay đổi của số lượng đơn hàng so với dữ liệu trước đó

    private double productsPctDiff; // Phần trăm thay đổi của số lượng sản phẩm so với dữ liệu trước đó

    private double conversionRatePctDiff; // Phần trăm thay đổi của tỷ lệ chuyển đổi so với dữ liệu trước đó

    private List<TimeSeriesData> timeSeries = new ArrayList<>(); // Danh sách dữ liệu thống kê theo thời gian

}


