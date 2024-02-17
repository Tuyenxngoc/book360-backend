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

    private boolean isDefaultAddress;

    private String addressName;

    private double latitude;

    private double longitude;

    public GetAddressResponseDto(AddressDetail addressDetail) {
        this.setCreatedDate(addressDetail.getCreatedDate());
        this.setLastModifiedDate(addressDetail.getLastModifiedDate());

        this.id = addressDetail.getId();
        this.fullName = addressDetail.getFullName();
        this.phoneNumber = addressDetail.getPhoneNumber();
        this.type = addressDetail.getType();
        this.isDefaultAddress = addressDetail.isDefaultAddress();

        Address address = addressDetail.getAddress();
        this.addressName = address.getAddressName();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
    }
}
