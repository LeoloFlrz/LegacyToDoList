package Springboot.com.TodoListApi.services;

import Springboot.com.TodoListApi.entities.Task;
import Springboot.com.TodoListApi.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private List<Task> tasks;

    @BeforeEach
    public void setUp() {
        tasks = Arrays.asList(
                new Task(1L, null, null, "Task 1", "Description 1", LocalDateTime.now(), true),
                new Task(2L, null, null, "Task 2", "Description 2", LocalDateTime.now(), false)
        );
    }

    @Test
    public void testCreateTask() {
        Task expectedTask = new Task();
        expectedTask.setId(1L);
        expectedTask.setTitle("Task 3");
        expectedTask.setDescription("Description 3");
        expectedTask.setIsCompleted(true);

        when(taskRepository.save(expectedTask)).thenReturn(expectedTask);

        Task createdTask = taskService.createTask(expectedTask);

        assertEquals(expectedTask, createdTask);
    }

    @Test
    public void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> allTasks = taskService.getAllTask();

        assertEquals(tasks.size(), allTasks.size());
    }

    @Test
    public void testGetTaskById() {
        Task expectedTask = tasks.get(0);

        when(taskRepository.findById(expectedTask.getId())).thenReturn(Optional.of(expectedTask));

        Optional<Task> retrievedTask = taskService.getTaskById(expectedTask.getId());

        assertTrue(retrievedTask.isPresent());

        assertEquals(expectedTask, retrievedTask.get());
    }

    @Test
    public void testUpdateTaskStatus() {
        Task taskToUpdate = tasks.get(0);
        taskToUpdate.setIsCompleted(false);

        when(taskRepository.save(taskToUpdate)).thenReturn(taskToUpdate);

        taskService.updateTaskStatus(taskToUpdate);

        assertFalse(taskToUpdate.getIsCompleted());
    }

    @Test
    public void testDeleteTask() {
        Long taskIdToDelete = 1L;

        when(taskRepository.findById(taskIdToDelete)).thenReturn(Optional.of(tasks.get(0)));

        taskService.deleteTask(taskIdToDelete);

        verify(taskRepository).deleteById(taskIdToDelete);

        when(taskRepository.findById(taskIdToDelete)).thenReturn(Optional.empty());
        Optional<Task> deletedTask = taskService.getTaskById(taskIdToDelete);
        assertFalse(deletedTask.isPresent());

    }

    @Test
    public void testDeleteTaskTaskNotExists() {
        Long taskIdToDelete = 3L;

        when(taskRepository.findById(taskIdToDelete)).thenReturn(Optional.empty());

        taskService.deleteTask(taskIdToDelete);
    }
}