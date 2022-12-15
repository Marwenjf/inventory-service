package com.jf.invintoryservice.repository;

import com.jf.invintoryservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
