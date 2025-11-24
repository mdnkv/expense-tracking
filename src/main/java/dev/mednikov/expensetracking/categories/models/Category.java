package dev.mednikov.expensetracking.categories.models;

import dev.mednikov.expensetracking.users.models.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name = "categories_category")
public class Category implements Comparable<Category> {

    @Id
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(user, category.user) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, name);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final class CategoryBuilder {
        private Long id;
        private User user;
        private String name;

        public CategoryBuilder() {
        }

        public CategoryBuilder(Category other) {
            this.id = other.id;
            this.user = other.user;
            this.name = other.name;
        }

        public CategoryBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public CategoryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.setName(name);
            category.user = this.user;
            category.id = this.id;
            return category;
        }
    }

}
