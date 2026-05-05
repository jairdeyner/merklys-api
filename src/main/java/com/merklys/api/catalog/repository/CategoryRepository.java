package com.merklys.api.catalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.merklys.api.catalog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("""
            SELECT c FROM Category c
            LEFT JOIN FETCH c.children
            WHERE c.parent IS NULL
            """)
    List<Category> findRootsWithChildren();
}
