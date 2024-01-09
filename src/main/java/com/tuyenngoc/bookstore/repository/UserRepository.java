package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndRefreshToken(String username, String refreshToken);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.refreshToken=?1 WHERE u.username=?2")
    void saveRefreshTokenByUsername(String refreshToken, String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.refreshToken='' WHERE u.username=?1")
    void removeRefreshToken(String username);
}
