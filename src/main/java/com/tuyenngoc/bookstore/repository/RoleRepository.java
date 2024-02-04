package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.constant.RoleConstant;
import com.tuyenngoc.bookstore.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleConstant roleName);

}
