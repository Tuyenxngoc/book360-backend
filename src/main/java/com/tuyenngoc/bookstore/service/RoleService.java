package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.constant.RoleConstant;
import com.tuyenngoc.bookstore.domain.entity.Role;

public interface RoleService {

    Role getRole(int roleId);

    Role getRole(RoleConstant roleConstant);

}
