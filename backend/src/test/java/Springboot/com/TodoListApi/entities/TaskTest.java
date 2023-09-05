package Springboot.com.TodoListApi.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    public User user = new User(1L, "leolo", "admin", "leolo@leolo.com", null, "");
    public Category category = new Category(1L, "testing");
    public Task firstTask = new Task(1L, user , category, "testing", "testeando", LocalDateTime.now() ,false);

    @Test
    void should_get_task_by_Id() {
        assertEquals(1L, firstTask.getId());
    }

    @Test
    void should_get_user_by_id() {
        assertEquals(1L, firstTask.getUser().getId());
    }

   @Test
    void should_get_tittle_from_task() {
        assertEquals("testing", firstTask.getTitle());
   }

   @Test
    void should_get_description_from_task() {
        assertEquals("testeando", firstTask.getDescription());
   }

   @Test
    void should_get_completed_status_from_task() {
        assertFalse(firstTask.getIsCompleted());
   }

   @Test
    void should_get_username_from_user() {
        assertEquals("leolo", user.getUsername());
   }

   @Test
    void should_get_password_from_user() {
        assertEquals("admin", user.getPassword());
   }

   @Test
    void should_get_email_from_user() {
        assertEquals("leolo@leolo.com", user.getEmail());
   }

   @Test
    void should_get_category_name_from_category() {
        assertEquals("testing", category.getTitle());
   }

    
}