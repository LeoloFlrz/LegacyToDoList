package Springboot.com.TodoListApi;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoListApiApplicationTests {

	@Autowired
	CategoryService categoryService;

	@Test
	void contextLoads() {
	}

	@Test
	void dumbTesting()
	{
		System.out.println("ID is:" + categoryService.getCategoryByTitle("Default").getId());
	}

}
