package com.example.moviewreviewapplication.repository;

import com.example.moviewreviewapplication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
