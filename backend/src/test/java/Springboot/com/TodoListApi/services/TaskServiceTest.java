package Springboot.com.TodoListApi.services;

import Springboot.com.TodoListApi.TodoListApiApplication;
import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.entities.Task;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    public User user = new User(1L, "leolo", "admin", "leolo@leolo.com", null, "");
    public Category category = new Category(1L, "testing");
    public Task firstTask = new Task(1L, user , category, "testing", "testeando", LocalDateTime.now() ,false);

    private static final Logger logger = Logger.getLogger(TaskServiceTest.class.getName());

    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;


    @Test
    public void testCreateTaskWithTitle() {
    String expectedTitle = "Tarea de prueba";
    Task task = new Task();
    task.setTitle(expectedTitle);

    when(taskRepository.save(task)).thenReturn(task);

    taskService.createTask(task);

    logger.info("expectedTitle: " + expectedTitle);
    logger.info("task.getTitle(): " + task.getTitle());

    assertEquals(expectedTitle, task.getTitle());
    }

    @Test
    void test_should_get_Task_By_Id() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(firstTask));

        Optional<Task> getTaskById = taskService.getTaskById(1L);

        assertEquals("testing", getTaskById.get().getTitle());
    }

    @Test
    void should_add_new_task() {

        Task postTask = new Task(2L, user , category, "test", "testing", LocalDateTime.now() ,false);

        when(taskRepository.save(postTask)).thenReturn(postTask);
        taskRepository.save(postTask);
        taskService.createTask(postTask);

        List<Task> allTasks = taskService.getAllTask();

        assertTrue(allTasks.contains(postTask));
    }

}