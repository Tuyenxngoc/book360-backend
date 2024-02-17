package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.constant.RoleConstant;
import com.tuyenngoc.bookstore.domain.dto.UserDto;
import com.tuyenngoc.bookstore.domain.dto.request.CreateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.RegisterRequestDto;
import com.tuyenngoc.bookstore.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequestDto requestDto);

    User toUser(CreateCustomerRequestDto requestDto);

    @Named("mapRoleName")
    default String getRoleName(RoleConstant roleConstant) {
        return roleConstant.getRoleName();
    }

    @Mappings({
            @Mapping(target = "roleName", source = "role.name", qualifiedByName = "mapRoleName"),
            @Mapping(target = "avatar", source = "customer.avatar"),
            @Mapping(target = "gender", source = "customer.gender"),
            @Mapping(target = "fullName", source = "customer.fullName"),
            @Mapping(target = "phoneNumber", source = "customer.phoneNumber"),
            @Mapping(target = "dob", source = "customer.dob")
    })
    UserDto toUserDto(User user);

}
