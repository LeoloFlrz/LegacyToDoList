package Springboot.com.TodoListApi.services;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.entities.Task;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    public User user = new User(1L, "leolo", "admin", "leolo@leolo.com", null, "");
    public Category category = new Category(1L, "testing", "testeando");
    public Task firstTask = new Task(1L, user , category, "testing", "testeando", LocalDateTime.now() ,false);

    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;


    @Test
    void test_should_get_Task_By_Id() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(firstTask));

        Optional<Task> getTaskById = taskService.getTaskById(1L);

        assertEquals("testing", getTaskById.get().getTitle());
    }

    @Test
    void should_add_new_task() {
        Task postTask = new Task(2L, user , category, "test", "testing", LocalDateTime.now() ,false);
        taskService.createTask(postTask);
        Optional<Task> currentTask = taskService.getTaskById(2L);
        //when(taskRepository.save(postTask)).thenReturn(postTask);
        assertEquals(true, currentTask.isPresent());
    }
}