package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.BookSetDto;
import com.tuyenngoc.bookstore.domain.entity.BookSet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookSetMapper {

    BookSet toBookSet(BookSetDto bookSetDto);

}
