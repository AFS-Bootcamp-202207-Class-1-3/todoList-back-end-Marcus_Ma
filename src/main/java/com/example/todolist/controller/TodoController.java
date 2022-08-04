package com.example.todolist.controller;

import com.example.todolist.dto.TodoRequest;
import com.example.todolist.entity.Todo;
import com.example.todolist.mapper.TodoMapper;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todos")
@CrossOrigin("*")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    TodoMapper todoMapper;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodoList(){
        return todoService.finAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo addTodo(@RequestBody TodoRequest newTodo){
        Todo todo = todoMapper.toEntity(newTodo);
        return todoService.add(todo);
    }

    @PutMapping("/{id}")
    public Todo changeTodo(@PathVariable Integer id,@RequestBody TodoRequest newTodo){
        Todo todo = todoMapper.toEntity(newTodo);
        if(todo.getContext()!=null){
            return todoService.changeTodoContext(id,todo);
        }else{
            return todoService.changeTodoDone(id,todo);
        }
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoById(@PathVariable Integer id){
        todoService.deleteTodoById(id);
    }
}
