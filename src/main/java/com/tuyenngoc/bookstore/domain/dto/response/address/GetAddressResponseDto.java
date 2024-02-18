package com.tuyenngoc.bookstore.domain.dto.response.address;

import com.tuyenngoc.bookstore.constant.AddressType;
import com.tuyenngoc.bookstore.domain.dto.common.DateAuditingDto;
import com.tuyenngoc.bookstore.domain.entity.Address;
import com.tuyenngoc.bookstore.domain.entity.AddressDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressResponseDto extends DateAuditingDto {

    private int id;

    private String fullName;

    private String phoneNumber;

    private AddressType type;

    private Boolean isDefaultAddress;

    private String state;

    private String district;

    private String ward;

    private String street;

    private String fullAddress;

    private double latitude;

    private double longitude;

    public GetAddressResponseDto(AddressDetail addressDetail) {
        this.setCreatedDate(addressDetail.getCreatedDate());
        this.setLastModifiedDate(addressDetail.getLastModifiedDate());

        this.id = addressDetail.getId();
        this.fullName = addressDetail.getFullName();
        this.phoneNumber = addressDetail.getPhoneNumber();
        this.type = addressDetail.getType();
        this.isDefaultAddress = addressDetail.getIsDefaultAddress();

        Address address = addressDetail.getAddress();
        this.state = address.getState();
        this.district = address.getDistrict();
        this.ward = address.getWard();
        this.street = address.getStreet();
        this.fullAddress = address.getFullAddress();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
    }
}
