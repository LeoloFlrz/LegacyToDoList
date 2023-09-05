package Springboot.com.TodoListApi.services;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public void saveCategory(Category category) {
        categoryRepository.save(category);

    }
    public List<Category> getAllCategories(){

        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(new Category());
    }

    public void deleteCategory(Long id){
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()){
            System.out.println("Category not exist with this ID: "+id);
        }
        categoryRepository.deleteById(id);

    }

}
