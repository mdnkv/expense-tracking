package dev.mednikov.expensetracking.categories.services;

import dev.mednikov.expensetracking.categories.dto.CategoryDto;
import dev.mednikov.expensetracking.categories.dto.CategoryDtoMapper;
import dev.mednikov.expensetracking.categories.exceptions.CategoryNotFoundException;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final static CategoryDtoMapper mapper = new CategoryDtoMapper();

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto request) {
        User user = this.userRepository.getReferenceById(request.userId());
        Category category = new Category.CategoryBuilder()
                .withUser(user)
                .withName(request.name())
                .build();
        Category result = this.categoryRepository.save(category);
        return mapper.apply(result);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto request) {
        Category category = this.categoryRepository
                .findById(request.id())
                .orElseThrow(CategoryNotFoundException::new);
        category.setName(request.name());

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
                .stream().map(mapper).toList();
    }
}
