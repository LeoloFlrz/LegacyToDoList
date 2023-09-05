package Springboot.com.TodoListApi.services;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.entities.Task;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    public User user = new User(1L, "leolo", "admin", "leolo@leolo.com", null, "");
    public Category category = new Category(1L, "testing");
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
        Task task = mock(Task.class);

        Task postTask = new Task(2L, user , category, "test", "testing", LocalDateTime.now() ,false);

        when(taskRepository.save(postTask)).thenReturn(postTask);
        //doThrow(new Exception()).doNothing().when(taskRepository.save(postTask));

        taskService.createTask(postTask);

        List<Task> allTasks = taskService.getAllTask();

        //Task currentTask = taskService.getTaskById(2L);

        assertTrue(allTasks.contains(postTask));
    }

    @Test
    void should_add_new_task_() {
        Task postTask = new Task(2L, user, category, "test", "testing", LocalDateTime.now(), false);


    }
}