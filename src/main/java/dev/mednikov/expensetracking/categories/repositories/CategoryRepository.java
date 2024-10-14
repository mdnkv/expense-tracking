package dev.mednikov.expensetracking.categories.repositories;

import dev.mednikov.expensetracking.categories.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUserId (Long userId);

}
