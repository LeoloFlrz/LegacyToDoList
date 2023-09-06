package Springboot.com.TodoListApi.initializers;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultCategoryInitializer implements CommandLineRunner
{
    @Autowired
    CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception
    {
        Category defaultCategory;

        defaultCategory = categoryService.getCategoryByTitle("Default");
        if (defaultCategory == null)
        {
            defaultCategory = new Category();
            defaultCategory.setTitle("Default");
            categoryService.saveCategory(defaultCategory);
        }
    }
}
