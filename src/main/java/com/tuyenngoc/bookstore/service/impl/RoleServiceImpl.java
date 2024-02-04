package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.RoleConstant;
import com.tuyenngoc.bookstore.domain.entity.Role;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.RoleRepository;
import com.tuyenngoc.bookstore.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRole(int roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Role.ERR_NOT_FOUND_ID, String.valueOf(roleId)));
    }

    @Override
    public Role getRole(RoleConstant roleConstant) {
        return roleRepository.findByName(roleConstant)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Role.ERR_NOT_FOUND_ID, roleConstant.getRoleName()));
    }
}
