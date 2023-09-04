package Springboot.com.TodoListApi.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    public User user = new User(1L, "leolo", "", "leolo@leolo.com", null, "");
    public Category category = new Category(1L, "testing", "testeando");
    public Task firstTask = new Task(1L, user , category, "testing", "testeando", LocalDateTime.now() ,false);

    @Test
    void getId() {
    }

    @Test
    void getUser() {
    }

    @Test
    void setUser() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void setCategory() {
    }

    @Test
    void getTitle() {
    }

    @Test
    void setTitle() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void setDescription() {
    }

    @Test
    void getIsCompleted() {
    }

    @Test
    void setIsCompleted() {
    }
}