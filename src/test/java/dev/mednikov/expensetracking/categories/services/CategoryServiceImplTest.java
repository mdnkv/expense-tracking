package dev.mednikov.expensetracking.categories.services;

import dev.mednikov.expensetracking.categories.dto.CategoryDto;
import dev.mednikov.expensetracking.categories.exceptions.CategoryNotFoundException;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private CategoryRepository categoryRepository;
    @InjectMocks private CategoryServiceImpl categoryService;

    @Test
    void createCategoryTest(){
        Long userId = 1L;
        User user = new User.UserBuilder().withId(userId).build();
        String categoryName = "Expenses";
        Long categoryId = 1L;
        Category category = new Category.CategoryBuilder()
                .withUser(user)
                .withId(categoryId)
                .withName(categoryName)
                .build();

        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto request = new CategoryDto(null, userId, categoryName);
        CategoryDto result = categoryService.createCategory(request);
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("id", categoryId)
                .hasFieldOrPropertyWithValue("name", categoryName);
    }

    @Test
    void updateCategory_doesNotExistTest(){
        Long userId = 1L;
        String categoryName = "Expenses";
        Long categoryId = 1L;

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        CategoryDto request = new CategoryDto(categoryId, userId, categoryName);
        Assertions
                .assertThatThrownBy(() -> categoryService.updateCategory(request))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void updateCategory_successTest(){
        Long userId = 1L;
        User user = new User.UserBuilder().withId(userId).build();
        String categoryName = "Expenses";
        Long categoryId = 1L;
        Category category = new Category.CategoryBuilder()
                .withUser(user)
                .withId(categoryId)
                .withName(categoryName)
                .build();

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto request = new CategoryDto(categoryId, userId, categoryName);
        CategoryDto result = categoryService.updateCategory(request);
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("id", categoryId)
                .hasFieldOrPropertyWithValue("name", categoryName);
    }

    @Test
    void findCategoryById_existsTest(){
        Long userId = 1L;
        User user = new User.UserBuilder().withId(userId).build();
        String categoryName = "Expenses";
        Long categoryId = 1L;
        Category category = new Category.CategoryBuilder()
                .withUser(user)
                .withId(categoryId)
                .withName(categoryName)
                .build();

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<CategoryDto> result = categoryService.findCategoryById(categoryId);
        Assertions.assertThat(result).isPresent();
    }

    @Test
    void findCategoryById_doesNotExistTest(){
        Long categoryId = 1L;
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Optional<CategoryDto> result = categoryService.findCategoryById(categoryId);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void findAllCategoriesForUserTest(){
        Long userId = 1L;
        User user = new User.UserBuilder().withId(userId).build();

        List<Category> categories = List.of(
                new Category.CategoryBuilder()
                        .withUser(user)
                        .withId(1L)
                        .withName("Rent")
                        .build(),
                new Category.CategoryBuilder()
                        .withUser(user)
                        .withId(2L)
                        .withName("Food")
                        .build(),
                new Category.CategoryBuilder()
                        .withUser(user)
                        .withId(3L)
                        .withName("Income")
                        .build()
        );

        Mockito.when(categoryRepository.findAllByUserId(userId)).thenReturn(categories);

        List<CategoryDto> result = categoryService.findAllCategoriesForUser(userId);
        Assertions.assertThat(result).hasSize(3);
    }

}
