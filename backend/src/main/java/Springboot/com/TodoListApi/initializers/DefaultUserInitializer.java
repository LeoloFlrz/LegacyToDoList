package Springboot.com.TodoListApi.initializers;

import Springboot.com.TodoListApi.entities.Category;
import Springboot.com.TodoListApi.entities.User;
import Springboot.com.TodoListApi.services.CategoryService;
import Springboot.com.TodoListApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer implements CommandLineRunner
{
    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception
    {
        User defaultUser;

        defaultUser = userService.getUserByUsername("Guest");
        if (defaultUser == null)
        {
            defaultUser = new User();
            defaultUser.setUsername("Guest");
            defaultUser.setEmail("guest@gmail.com");
            defaultUser.setPassword("guest1234");
            defaultUser.setProfilePicture("NA");
            userService.saveUser(defaultUser);
        }
    }
}
