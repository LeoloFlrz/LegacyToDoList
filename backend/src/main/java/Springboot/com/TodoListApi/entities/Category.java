package Springboot.com.TodoListApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.beans.ConstructorProperties;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public Category(long l, String testing, String testeando) {
    }
}
