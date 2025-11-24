package dev.mednikov.expensetracking.categories.services;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import dev.mednikov.expensetracking.categories.dto.CategoryDto;
import dev.mednikov.expensetracking.categories.dto.CategoryDtoMapper;
import dev.mednikov.expensetracking.categories.exceptions.CategoryNotFoundException;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final static SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
    private final static CategoryDtoMapper mapper = new CategoryDtoMapper();

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto request) {
        Long userId = Long.parseLong(request.getUserId());
        User user = this.userRepository.getReferenceById(userId);
        Category category = new Category.CategoryBuilder()
                .withId(snowflakeGenerator.next())
                .withUser(user)
                .withName(request.getName())
                .build();
        Category result = this.categoryRepository.save(category);
        return mapper.apply(result);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto request) {
        Objects.requireNonNull(request.getId());
        Long id = Long.parseLong(request.getId());
        Category category = this.categoryRepository
                .findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        category.setName(request.getName());

        Category result = this.categoryRepository.save(category);
        return mapper.apply(result);
    }

    @Override
    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public Optional<CategoryDto> findCategoryById(Long id) {
        return this.categoryRepository.findById(id).map(mapper);
    }

    @Override
    public List<CategoryDto> findAllCategoriesForUser(Long userId) {
        return this.categoryRepository
                .findAllByUserId(userId)
                .stream()
                .sorted()
                .map(mapper).toList();
    }
}
