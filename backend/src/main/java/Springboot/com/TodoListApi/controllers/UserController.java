package Springboot.com.TodoListApi.controllers;
import java.io.IOException;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/users")

public class UserController {
@Autowired
	private  UserService userService;

public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/webapp/Images";
	@PostMapping
	public ResponseEntity<String> createUser(@ModelAttribute User user,@RequestParam("image") MultipartFile file){
	String originalFilename = file.getOriginalFilename();
	Path fileNameAndPath = Paths.get(uploadDirectory,originalFilename);
	try {
		Files.write(fileNameAndPath,file.getBytes());
		user.setProfilePicture(originalFilename);
		userService.saveUser(user);
		return ResponseEntity.ok("User Created Successfully!");
	} catch (IOException e) {
		throw new RuntimeException(e);
	}

	}
	@GetMapping
	public ResponseEntity< List<User>> getUsers(){
	List<User> users=	userService.getAllUser();
	return ResponseEntity.ok(users);
	}
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User with this id is not exist" + id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> editeUser(@RequestBody User user,@PathVariable Long id){
		if(userService.getUserById(id).isPresent()) {
			userService.saveUser(user);
			System.out.println("Updated user with ID: "+id);
		}
		return  ResponseEntity.ok("Not Updated!");
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok("User Successfully Deleted");
		}
		catch (ResponseStatusException ex){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}

	}

}


