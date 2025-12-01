package dev.mednikov.expensetracking.categories.boostrap;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.users.events.NewUserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryBootstrapService {

    private final Logger logger = LoggerFactory.getLogger(CategoryBootstrapService.class);
    private final static SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();

    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public CategoryBootstrapService(CategoryRepository categoryRepository, ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    @EventListener
    public void onUserCreatedListener (NewUserCreatedEvent event){
        try {
            Resource resource = this.resourceLoader.getResource("classpath:bootstrap/categories.json");
            TypeReference<List<Category>> typeReference = new TypeReference<>() {};
            List<Category> data = this.objectMapper.readValue(resource.getInputStream(), typeReference);
            List<Category> categories = new ArrayList<>();
            for (Category loaded : data) {
                Category category = new Category();
                category.setName(loaded.getName());
                category.setId(snowflakeGenerator.next());
                category.setUser(event.getUser());
                categories.add(category);
            }
            categoryRepository.saveAll(categories);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

}
