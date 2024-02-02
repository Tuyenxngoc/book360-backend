package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.constant.PaymentMethod;
import com.tuyenngoc.bookstore.domain.dto.BillDetailDto;
import com.tuyenngoc.bookstore.domain.dto.request.BillRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetBillDetailResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BillMapper {

    Bill toBill(BillRequestDto requestDto);

    @Named("mapPaymentMethodToString")
    default String getPaymentMethodName(PaymentMethod paymentMethod) {
        return paymentMethod.getDescription();
    }

    @Named("mapBillStatusToString")
    default String getBillStatusName(BillStatus billStatus) {
        return billStatus.getName();
    }

    @Named("mapBillDetails")
    default List<BillDetailDto> getBillDetailsDtos(List<BillDetail> billDetails) {
        List<BillDetailDto> billDetailsDtos = new ArrayList<>();
        for (BillDetail billDetail : billDetails) {
            billDetailsDtos.add(new BillDetailDto(billDetail));
        }
        return billDetailsDtos;
    }

    @Mappings({
            @Mapping(target = "paymentMethod", source = "paymentMethod", qualifiedByName = "mapPaymentMethodToString"),
            @Mapping(target = "billStatus", source = "billStatus", qualifiedByName = "mapBillStatusToString"),
            @Mapping(target = "billDetails", source = "billDetails", qualifiedByName = "mapBillDetails"),
    })
    GetBillDetailResponseDto toGetBillDetailResponseDto(Bill bill);
}
