package Springboot.com.TodoListApi.controllers;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.entities.Task;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<String> AddCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
        return ResponseEntity.ok("Category Successfully Created!");
    }
    @GetMapping
    public ResponseEntity<List<Category>> AllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    /*@GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category with this id is not exist" + id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> editeCategory(@RequestBody Category category,@PathVariable Long id){
        if(categoryService.getCategoryById(id).isPresent()) {
            categoryService.saveCategory(category);
            System.out.println("Updated category with ID: "+id);
        }
        return  ResponseEntity.ok("Not Updated!");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){

        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category Successfully Deleted");
        }
        catch (ResponseStatusException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }*/
}