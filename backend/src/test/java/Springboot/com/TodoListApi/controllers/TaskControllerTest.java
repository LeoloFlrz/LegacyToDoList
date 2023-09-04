package Springboot.com.TodoListApi.controllers;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.entities.Task;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.repositories.TaskRepository;
import Springboot.com.TodoListApi.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    public User user = new User(1L, "leolo", "admin", "leolo@leolo.com", null, "");
    public Category category = new Category(1L, "testing", "testeando");
    public Task firstTask = new Task(1L, user , category, "testing", "testeando", LocalDateTime.now() ,false);

    
    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;


    @Test
    void test_should_get_Task_By_Id() {
        //BBDD
       

        when(taskRepository.findById(1L)).thenReturn(Optional.of(firstTask));

        Optional<Task> getTaskById = taskService.getTaskById(1L);

        assertEquals("testing", getTaskById.get().getTitle());
    }


    @Test
    void addTask() {
    }

    @Test
    void allTask() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void editeTask() {
    }

    @Test
    void updateCompletionStatus() {
    }

    @Test
    void deleteTask() {
    }
}