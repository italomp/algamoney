package com.algaworks.algamoney_api.algamoney_api.repository;

import com.algaworks.algamoney_api.algamoney_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
