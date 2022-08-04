package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import com.example.todolist.exception.NotFoundException;
import com.example.todolist.repository.JpaTodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    JpaTodoRepository jpaTodoRepository;

    public TodoService(JpaTodoRepository jpaTodoRepository) {
        this.jpaTodoRepository = jpaTodoRepository;
    }
    public List<Todo> finAll(){
        return jpaTodoRepository.findAll();
    }

    public Todo add(Todo newTodo) {
        return jpaTodoRepository.save(newTodo);
    }

    public Todo changeTodoDone(Integer id, Todo newTodo) {
        Todo todo = jpaTodoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(Todo.class.getSimpleName()));
        todo.setDone(newTodo.getDone());
        return jpaTodoRepository.save(todo);
    }
}
