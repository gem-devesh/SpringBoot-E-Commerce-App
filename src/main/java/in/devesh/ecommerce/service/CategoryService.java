package in.devesh.ecommerce.service;

import in.devesh.ecommerce.model.Category;
import in.devesh.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    public void removeCategoryById(int id) {
        categoryRepository.deleteById(id);
    }
    public Optional<Category> findCategoryById(int id) {
        return categoryRepository.findById(id);
    }
}
