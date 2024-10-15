package com.kevinpina.shopping.management.infrastructure.repository.db.repository;

import com.kevinpina.shopping.management.infrastructure.repository.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query(value = "SELECT * FROM shopping_management.users AS u WHERE u.user_id = :userId", nativeQuery = true)
    List<UserEntity> findUserById(String userId);

}
