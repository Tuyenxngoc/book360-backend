package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.UserDto;
import com.tuyenngoc.bookstore.domain.dto.request.RegisterRequestDto;
import com.tuyenngoc.bookstore.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequestDto requestDto);

    @Mappings({
            @Mapping(target = "roleName", source = "role.name"),
            @Mapping(target = "avatar", source = "customer.avatar"),
            @Mapping(target = "address", source = "customer.address.addressName"),
            @Mapping(target = "gender", source = "customer.gender"),
            @Mapping(target = "customerName", source = "customer.fullName")
    })
    UserDto toUserDto(User user);

}
