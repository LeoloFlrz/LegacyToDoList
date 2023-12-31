package Springboot.com.TodoListApi.controllers;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.entities.Task;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.services.CategoryService;
import Springboot.com.TodoListApi.services.TaskService;
import Springboot.com.TodoListApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {
	@Autowired
	private  TaskService taskService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@PostMapping()
	public ResponseEntity<String> AddTask(@RequestBody Task task){
	User selectedUser = userService.getAllUser().stream().filter(user -> user.getId().equals(task.getUser().getId())).findFirst().
			orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with ID: "+ task.getUser().getId()));
	Category selectedCategory;

	selectedCategory = categoryService.getCategoryByTitle(task.getCategory().getTitle());
	if (selectedCategory == null)
	{
		selectedCategory = new Category();
		selectedCategory.setTitle(task.getCategory().getTitle());
		categoryService.saveCategory(selectedCategory);
	}
	task.setUser((selectedUser));
	task.setCategory(selectedCategory);
		 taskService.createTask(task);
		 return ResponseEntity.ok("Task Successfully Created!");
	}
	@GetMapping
	public ResponseEntity< List<Task>> AllTask(){
		List<Task> tasks = taskService.getAllTask();
		return ResponseEntity.ok(tasks);
	}
	@GetMapping("/{id}")
	public Task getTaskById(@PathVariable Long id) {
		return taskService.getTaskById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User with this id is not exist" + id));
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> editeTask(@RequestBody Task task, @PathVariable Long id) {
		Category updatedCategory;
		if (taskService.getTaskById(id).isPresent()) {
			User selectedUser = userService.getAllUser().stream().filter(user -> user.getId().equals(task.getUser().getId())).findFirst().
					orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with ID: "+ task.getUser().getId()));
			task.setUser((selectedUser));

			updatedCategory = categoryService.getCategoryByTitle(task.getCategory().getTitle());
			task.setCategory(updatedCategory);
			taskService.updateTaskStatus(task);

			return ResponseEntity.ok("Task Updated!");
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<String> updateCompletionStatus(@PathVariable Long id) {
		Optional<Task> taskOptional = taskService.getTaskById(id);

		if (taskOptional.isPresent()) {
			Task task = taskOptional.get();
			task.setIsCompleted(!task.getIsCompleted());
			taskService.updateTaskStatus(task);

			return ResponseEntity.ok("Completion Status Updated!");
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}/deleted")
	public ResponseEntity<String> deleteTask(@PathVariable Long id){

		taskService.deleteTask(id);

		try {
			taskService.deleteTask(id);
			return ResponseEntity.ok("Task Successfully Deleted");
		}
		catch (ResponseStatusException ex){

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}

	}

}