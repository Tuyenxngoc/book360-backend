package com.bookstore.bookstore.domain.mapper;

import com.bookstore.bookstore.domain.dto.response.UserDto;
import com.bookstore.bookstore.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "roleName", source = "role.name"),
            @Mapping(target = "customerId", source = "customer.id")
    })
    UserDto toUserDto(User user);

}
