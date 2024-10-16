package com.kevinpina.shopping.management.infrastructure.repository.db.repository;

import com.kevinpina.shopping.management.infrastructure.repository.db.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {

    @Query(value = "SELECT * FROM shopping_management.item_product AS i WHERE i.username = :username", nativeQuery = true)
    List<ItemEntity> findByUserName(String username);

}
